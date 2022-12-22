import { createContext, ReactNode, useContext, useState, } from 'react';
import { toast } from 'react-toastify';
import { api } from '../services/api';
import { Product, Stock, OrderResponse } from '../types';
import { useNavigate } from 'react-router-dom';
import { convertCartObjectToOrderObject } from '../util/convertObject';


interface CartProviderProps {
    children: ReactNode;
}

interface UpdateProductAmount {
    productId: number;
    amount: number;
}

interface CartContextData {
    cart: Product[];
    addProduct: (productId: number) => Promise < void > ;
    removeProduct: (productId: number) => void;
    updateProductAmount: ({
        productId,
        amount
    }: UpdateProductAmount) => void;
    submitOrder: () => void;
    orderedItems: OrderResponse;
}

const CartContext = createContext < CartContextData > ({} as CartContextData);

export function CartProvider({
    children
}: CartProviderProps): JSX.Element {
    const [cart, setCart] = useState < Product[] > (() => {
        const storedCart = localStorage.getItem('@David:cart')

        if (storedCart) {
            return JSON.parse(storedCart);
        }

        return [];
    });
    const [orderedItems, setOrderedItems] = useState < OrderResponse > ();
    const navigate = useNavigate();


    const addProduct = async (productId: number) => {
        try {
            const productAlreadyInCart = cart.find(product => product.id === productId)

            if (!productAlreadyInCart) {
                const {
                    data: product
                } = await api.get < Product > (`products/${productId}`)
                const {
                    data: stock
                } = await api.get < Stock > (`products/stock/${productId}`)

                if (stock.qtyInStock > 0) {
                    setCart([...cart, {
                        ...product,
                        amount: 1
                    }])
                    localStorage.setItem('@David:cart', JSON.stringify([...cart, {
                        ...product,
                        amount: 1
                    }]))
                    toast('Added to cart')
                    return;
                }
            }

            if (productAlreadyInCart) {
                const {
                    data: stock
                } = await api.get(`products/stock/${productId}`)

                if (stock.qtyInStock > productAlreadyInCart.amount) {
                    const updatedCart = cart.map(cartItem => cartItem.id === productId ? {
                        ...cartItem,
                        amount: Number(cartItem.amount) + 1
                    } : cartItem)

                    setCart(updatedCart)
                    localStorage.setItem('@David:cart', JSON.stringify(updatedCart))
                    return;
                } else {
                    toast.error('Quantity required is out of stock')
                }
            }
        } catch {
            toast.error('Error adding the product')
        }
    };

    const removeProduct = (productId: number) => {
        try {
            const productExists = cart.some(cartProduct => cartProduct.id === productId)
            if (!productExists) {
                toast.error('Error deleting the product');
                return
            }

            const updatedCart = cart.filter(cartItem => cartItem.id !== productId)
            setCart(updatedCart)
            localStorage.setItem('@David:cart', JSON.stringify(updatedCart))
        } catch {
            toast.error('Error deleting the product');
        }
    };

    const updateProductAmount = async ({
        productId,
        amount,
    }: UpdateProductAmount) => {
        try {
            if (amount < 1) {
                toast.error('Error updating product quantity');
                return
            }

            const response = await api.get(`products/stock/${productId}`);
            const productAmount = response.data.amount;
            const stockIsFree = amount > productAmount

            if (stockIsFree) {
                toast.error('Quantity required is out of stock')
                return
            }

            const productExists = cart.some(cartProduct => cartProduct.id === productId)
            if (!productExists) {
                toast.error('Error updating product quantity');
                return
            }

            const updatedCart = cart.map(cartItem => cartItem.id === productId ? {
                ...cartItem,
                amount: amount
            } : cartItem)
            setCart(updatedCart)
            localStorage.setItem('@David:cart', JSON.stringify(updatedCart))
        } catch {
            toast.error('Error updating product quantity');
        }
    };

    const submitOrder = async () => {
        try {

            const response = await api.post(`order`, convertCartObjectToOrderObject(cart));
            setOrderedItems(response.data);
            localStorage.setItem('@David:order', JSON.stringify(response.data))
            return navigate('/order');
        } catch {
            toast.error('Error submitting your order');
        }
    }



    return ( <
        CartContext.Provider value = {
            {
                cart,
                addProduct,
                removeProduct,
                updateProductAmount,
                submitOrder,
                orderedItems
            }
        } >
        {
            children
        } </CartContext.Provider>
    );
}

export function useCart(): CartContextData {
    const context = useContext(CartContext);

    return context;
}
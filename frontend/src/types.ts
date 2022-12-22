export interface Stock {
  id: number;
  qtyInStock: number;
}

export type SpringPage<T> = {
  content: T[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  numberOfElements?: number;
  empty: boolean;
};

export interface Product  {
    id: number,
    name: string,
    description: string,
    price:number,
    imgUrl: string,
    amount: number
}

export interface Order {
  items: [
          {
            productId: number, 
            quantity: number,
            price: number
          }
        ]
}

export interface OrderResponse {
    id: number,
   date: string,
   totalAmount: number,
   itemsQuantity :number,
  items: [
          { id: number, 
            date: string, 
            productId: number, 
            quantity: number,
            price: number
            product: {
               id: number,
              name: string,
              description: string,
              price:number,
              imgUrl: string,
              qtyInStock: number
            }
          }
        ]
}
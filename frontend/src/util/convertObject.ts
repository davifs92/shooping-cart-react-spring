import { Product, Order } from './../types';

 export function convertCartObjectToOrderObject (cart: Product[]) {
     let orderObject: Order = { items: [
          {
            productId: 0, 
            quantity: 0,
            price: 0
          }
        ]};

   cart.forEach((item, i) => {
          let itemModel = [{
            productId: item.id , 
            quantity: item.amount,
            price: item.price
           }]
           
       orderObject.items.push(itemModel[0]);
    })
    orderObject.items.shift()
    return orderObject;

  }
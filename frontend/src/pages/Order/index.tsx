import { useState } from "react";
import { OrderResponse } from "../../types";
import { formatPrice } from "../../util/format";
import { Container, ProductTable, Total } from "../Cart/styles";

const Order = (): JSX.Element => {

   const [orderedItems, setOrderedItems] = useState<OrderResponse>(() => {
   const storedOrder = localStorage.getItem('@David:order')

    if (storedOrder) {
      return JSON.parse(storedOrder);
    }

    return {};
  });
    return (
        <Container>
            <h1>Order n° {orderedItems.id} successfully submitted!</h1>
      <ProductTable>
        <thead>
          <tr>
            <th aria-label="product image" />
            <th>PRODUCT</th>
            <th>QTY</th>
            <th>PRICE</th>
            <th aria-label="delete icon" />
          </tr>
        </thead>
        <tbody>
          {orderedItems.items.map(item => (
            <tr data-testid="product" key={item.productId}>
            <td>
              <img src={item.product.imgUrl} alt={item.product.name} />
            </td>
            <td>
              <span>{item.product.name}</span>
            </td>
            <td>
              <div>                
                <span>{item.quantity}</span>                        
              </div>
            </td>
            <td>
             <strong>{formatPrice(item.product.price)}</strong>

            </td>
                </tr>
          ))}
        </tbody>
      </ProductTable>

      <footer>
        <Total>
          <span>TOTAL</span>
          <strong>{formatPrice(orderedItems.totalAmount)}</strong>
        </Total>
      </footer>
    </Container>
    )

}

export default Order;

import { Routes as Switch, Route } from 'react-router-dom';

import Home from './pages/Home';
import Cart from './pages/Cart';
import Order from './pages/Order';

const Routes = (): JSX.Element => {
  return (
    <Switch>
      <Route path="/" element={ <Home />} />
      <Route path="/cart" element={<Cart />} />
      <Route path="/order" element={<Order />} />      
    </Switch>
  );
};

export default Routes;

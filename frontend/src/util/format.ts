export const { format: formatPrice } = new Intl.NumberFormat('en-ca', {
  style: 'currency',
  currency: 'CAD',
});

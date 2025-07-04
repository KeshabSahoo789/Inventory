document.addEventListener('DOMContentLoaded', function () {
  // Mocked subtotal value (🟢 replace this with value passed from cart page)
  const subtotalValue = 300.00;

  // Get elements
  const subtotalSpan = document.getElementById('subtotal');
  const gstSpan = document.getElementById('gstAmount');
  const totalSpan = document.getElementById('totalAmount');

  // Calculate GST and Total
  const gst = +(subtotalValue * 0.18).toFixed(2);
  const total = +(subtotalValue + gst).toFixed(2);

  // Display values
  subtotalSpan.textContent = subtotalValue.toFixed(2);
  gstSpan.textContent = gst.toFixed(2);
  totalSpan.textContent = total.toFixed(2);

  // Confirm alert
  document.getElementById('checkoutForm').addEventListener('submit', function (e) {
    e.preventDefault();
    alert("Order Confirmed Successfully ✅");
  });
});

document.addEventListener("DOMContentLoaded", function() {
  // Get customers from localStorage
  const customers = JSON.parse(localStorage.getItem('customers') || '[]');
  
  // Get customer select dropdown
  const customerSelect = document.getElementById("customerSelect");
  
  // Clear existing options
  customerSelect.innerHTML = "";
  
  // Add default option
  const defaultOption = document.createElement("option");
  defaultOption.value = "";
  defaultOption.textContent = "-- Select a customer --";
  customerSelect.appendChild(defaultOption);
  
  // Add customer options
  customers.forEach(customer => {
    const option = document.createElement("option");
    option.value = customer.id;
    option.textContent = customer.name;
    customerSelect.appendChild(option);
  });
});

document.getElementById("loadInvoices").addEventListener("click", function () {
  const customerId = document.getElementById("customerSelect").value;
  
  if (!customerId) {
    alert("Please select a customer");
    return;
  }

  // Dummy data for invoices - in a real app, you'd filter by customer ID
  const invoices = [
    { id: 1, date: "2025-04-01", amount: 250, status: "Paid" },
    { id: 2, date: "2025-03-15", amount: 180, status: "Unpaid" },
    { id: 3, date: "2025-02-28", amount: 300, status: "Paid" },
  ];

  const invoiceList = document.getElementById("invoiceList");
  invoiceList.innerHTML = ""; // Clear existing list

  invoices.forEach((invoice) => {
    const div = document.createElement("div");
    div.className = "invoice-item";
    div.innerHTML = `
      <div>
        <strong>Invoice #${invoice.id}</strong><br>
        Date: ${invoice.date}<br>
        Amount: $${invoice.amount}
      </div>
      <div>Status: ${invoice.status}</div>
    `;
    invoiceList.appendChild(div);
  });
});
  
document.addEventListener("DOMContentLoaded", function() {
  const generateForm = document.getElementById("generateInvoiceForm");
  const customerSelect = document.getElementById("customerSelect");
  const monthSelect = document.getElementById("billingMonth");
  const yearSelect = document.getElementById("billingYear");
  const invoicePreview = document.getElementById("invoicePreview");
  
  // Get customers from localStorage
  const customers = JSON.parse(localStorage.getItem('customers') || '[]');
  
  // Populate customer dropdown
  customers.forEach(customer => {
    const option = document.createElement("option");
    option.value = customer.id;
    option.textContent = customer.name;
    customerSelect.appendChild(option);
  });
  
  // Generate monthly select options
  const months = ["January", "February", "March", "April", "May", "June", 
                 "July", "August", "September", "October", "November", "December"];
  
  months.forEach((month, index) => {
    const option = document.createElement("option");
    option.value = index + 1;
    option.textContent = month;
    monthSelect.appendChild(option);
  });
  
  // Generate year options (current year and previous 2 years)
  const currentYear = new Date().getFullYear();
  for (let year = currentYear; year >= currentYear - 2; year--) {
    const option = document.createElement("option");
    option.value = year;
    option.textContent = year;
    yearSelect.appendChild(option);
  }
  
  // Handle form submission
  generateForm.addEventListener("submit", function(e) {
    e.preventDefault();
    
    const customerId = customerSelect.value;
    const month = parseInt(monthSelect.value);
    const year = parseInt(yearSelect.value);
    
    if (!customerId) {
      alert("Please select a customer");
      return;
    }
    
    // Generate invoice
    generateInvoice(customerId, month, year);
  });
  
  // Generate invoice function
  function generateInvoice(customerId, month, year) {
    // Get customer data
    const customer = customers.find(c => c.id === customerId);
    
    if (!customer) {
      showToast("Customer not found");
      return;
    }
    
    // Get assigned services
    const assignedServices = JSON.parse(localStorage.getItem('assignedServices') || '[]')
      .filter(as => as.customerId === customerId);
    
    // Get charges from CDR
    const charges = JSON.parse(localStorage.getItem('charges') || '[]')
      .filter(charge => {
        const chargeDate = new Date(charge.date);
        return charge.customerId === customerId && 
               chargeDate.getMonth() + 1 === month && 
               chargeDate.getFullYear() === year;
      });
    
    // Calculate recurring charges
    const recurringCharges = assignedServices
      .filter(as => as.serviceType === 'recurring')
      .reduce((sum, service) => sum + service.price, 0);
    
    // Calculate one-time charges
    const oneTimeCharges = assignedServices
      .filter(as => as.serviceType === 'one-time')
      .reduce((sum, service) => sum + service.price, 0);
    
    // Calculate usage charges
    const usageCharges = charges.reduce((sum, charge) => sum + charge.amount, 0);
    
    // Calculate total
    const total = recurringCharges + oneTimeCharges + usageCharges;
    
    // Generate invoice ID
    const invoiceId = `INV-${customerId}-${year}${month.toString().padStart(2, '0')}-${Date.now().toString().slice(-4)}`;
    
    // Create invoice object
    const invoice = {
      id: invoiceId,
      customerId: customerId,
      customerName: customer.name,
      month: month,
      year: year,
      issueDate: new Date().toISOString().split('T')[0],
      dueDate: new Date(new Date().setDate(new Date().getDate() + 15)).toISOString().split('T')[0],
      recurringCharges: recurringCharges,
      oneTimeCharges: oneTimeCharges,
      usageCharges: usageCharges,
      total: total,
      status: 'Unpaid'
    };
    
    // Save invoice
    const invoices = JSON.parse(localStorage.getItem('invoices') || '[]');
    invoices.push(invoice);
    localStorage.setItem('invoices', JSON.stringify(invoices));
    
    // Display invoice preview
    displayInvoicePreview(invoice, customer, assignedServices, charges);
    
    function showToast(message) {
  const toast = document.getElementById("toast");
  toast.textContent = message;
  toast.classList.add("show");

  setTimeout(() => {
    toast.classList.remove("show");
  }, 1000); // يظهر لمدة 2 ثانية
}
showToast("Invoice generated successfully");

  }
  
function displayInvoicePreview(invoice, customer, assignedServices, charges) {
  invoicePreview.innerHTML = `
    <div class="invoice-box" style="
      max-width: 800px;
      margin: auto;
      padding: 30px;
      border: 1px solid #eee;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
      font-size: 16px;
      line-height: 24px;
      font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;
      color: #555;
    ">
      <table style="width: 100%; line-height: inherit; text-align: left;">
        <tr class="top">
          <td colspan="2" style="padding-bottom: 20px;">
            <table style="width: 100%;">
              <tr>
                <td class="title">
                  <h2 style="margin: 0;">Fatora</h2>
                  <small>www.Fatora.com</small>
                </td>
                <td style="text-align: right;">
                  <strong>Invoice #:</strong> ${invoice.id}<br />
                  <strong>Issue Date:</strong> ${invoice.issueDate}<br />
                  <strong>Due Date:</strong> ${invoice.dueDate}
                </td>
              </tr>
            </table>
          </td>
        </tr>
        
        <tr class="information">
          <td colspan="2" style="padding-bottom: 40px;">
            <table style="width: 100%;">
              <tr>
                <td>
                  <strong>Bill To:</strong><br />
                  ${customer.name}<br />
                  Phone: ${customer.phone}<br />
                  Email: ${customer.email}<br />
                  Profile: ${customer.profile}
                </td>
                <td style="text-align: right;">
                  <strong>Fatora</strong><br />
                  1234 Main Street<br />
                  Cairo, Egypt<br />
                  contact@Fatora.com
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr class="heading">
          <td><strong>Charge Type</strong></td>
          <td style="text-align: right;"><strong>Amount</strong></td>
        </tr>

        <tr class="item">
          <td>Recurring Charges</td>
          <td style="text-align: right;">$${invoice.recurringCharges.toFixed(2)}</td>
        </tr>
        <tr class="item">
          <td>One-time Charges</td>
          <td style="text-align: right;">$${invoice.oneTimeCharges.toFixed(2)}</td>
        </tr>
        <tr class="item last">
          <td>Usage Charges</td>
          <td style="text-align: right;">$${invoice.usageCharges.toFixed(2)}</td>
        </tr>

        <tr class="total">
          <td></td>
          <td style="text-align: right; font-size: 18px; padding-top: 10px;">
            <strong>Total: $${invoice.total.toFixed(2)}</strong>
          </td>
        </tr>
      </table>

      <br /><br />
      <p style="text-align: center; color: #888;">
        Thank you for your business!
      </p>

      <p style="text-align: right; margin-top: 50px;">
        ___________________________<br />
        Authorized Signature
      </p>

      <div style="text-align: center; margin-top: 20px;">
        <button id="printInvoice" class="btn" style="
          background: #007bff;
          color: white;
          padding: 10px 20px;
          border: none;
          border-radius: 4px;
          cursor: pointer;
        ">Print Invoice</button>
      </div>
    </div>
  `;

  // Add print functionality
  document.getElementById("printInvoice").addEventListener("click", function () {
    window.print();
  });
}

});
  
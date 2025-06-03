document.addEventListener("DOMContentLoaded", function() {
  // Load customers from localStorage when page loads
  const customersList = JSON.parse(localStorage.getItem('customers') || '[]');
  
  // Display number of customers
  const statsElem = document.createElement("p");
  statsElem.textContent = `Total customers: ${customersList.length}`;
  document.querySelector(".form-container").insertBefore(statsElem, document.getElementById("searchResults"));
});

document.getElementById("searchForm").addEventListener("submit", function (e) {
  e.preventDefault();
  const query = document.getElementById("searchQuery").value.toLowerCase();

  // Get customers from localStorage
  const customers = JSON.parse(localStorage.getItem('customers') || '[]');

  const resultsContainer = document.getElementById("searchResults");
  resultsContainer.innerHTML = "";

  const results = customers.filter(
    (c) =>
      c.name.toLowerCase().includes(query) ||
      c.phone.includes(query) ||
      (c.email && c.email.toLowerCase().includes(query))
  );

  if (results.length > 0) {
    results.forEach((c) => {
      const div = document.createElement("div");
      div.className = "result-item";
      div.innerHTML = `
        <strong>${c.name}</strong> - ${c.phone}<br>
        <small>Email: ${c.email || 'N/A'} | Profile: ${c.profile}</small>
      `;
      resultsContainer.appendChild(div);
    });
  } else {
    resultsContainer.innerHTML = "<p>No matching customers found.</p>";
  }
});
  
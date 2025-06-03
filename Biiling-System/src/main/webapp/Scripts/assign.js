document.addEventListener("DOMContentLoaded", function () {
  const customerSelect = document.getElementById("customerSelect");
  const serviceTypeSelect = document.getElementById("serviceType");
  const servicePackageSelect = document.getElementById("servicePackage");

  const customers = JSON.parse(localStorage.getItem("customers") || "[]");
  const servicePackages = JSON.parse(localStorage.getItem("servicePackages") || "[]");

  // Populate customer dropdown
  customers.forEach(customer => {
    const option = document.createElement("option");
    option.value = customer.id;
    option.textContent = customer.name;
    customerSelect.appendChild(option);
  });

  // Populate service package dropdown based on selected service type
  serviceTypeSelect.addEventListener("change", function () {
    const serviceType = this.value;
    servicePackageSelect.innerHTML = '<option value="">-- Select a package --</option>';

    const filteredPackages = servicePackages.filter(pkg => pkg.type === serviceType);
    filteredPackages.forEach(pkg => {
      const option = document.createElement("option");
      option.value = pkg.id;
      option.textContent = `${pkg.name} - $${pkg.price}`;
      servicePackageSelect.appendChild(option);
    });
  });

  // Handle form submission
  document.getElementById("assignServiceForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const customerId = customerSelect.value;
    const serviceType = serviceTypeSelect.value;
    const servicePackageId = parseInt(servicePackageSelect.value);

    const customer = customers.find(c => c.id === customerId);
    const servicePackage = servicePackages.find(sp => sp.id === servicePackageId);

    if (!customer) return alert("Customer not found");
    if (!servicePackage) return alert("Service package not found");

    const assignedServices = JSON.parse(localStorage.getItem("assignedServices") || "[]");

    const assignment = {
      id: Date.now().toString(),
      customerId,
      servicePackageId,
      serviceType,
      price: servicePackage.price,
      assignedDate: new Date().toISOString().split("T")[0]
    };

    assignedServices.push(assignment);
    localStorage.setItem("assignedServices", JSON.stringify(assignedServices));

    alert("Service assigned successfully");
    this.reset();
  });
});

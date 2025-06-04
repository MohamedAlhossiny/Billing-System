// Populate profile dropdown when the page loads
document.addEventListener("DOMContentLoaded", function() {
  // Check if we're on the add customer page
  const profileSelect = document.getElementById("profile");
  if (profileSelect) {
    // Make sure profiles are defined
    if (typeof profiles !== 'undefined') {
      // Clear existing options
      profileSelect.innerHTML = "";
      
      // Add default option
      const defaultOption = document.createElement("option");
      defaultOption.value = "";
      defaultOption.textContent = "-- Select a profile --";
      profileSelect.appendChild(defaultOption);
      
      // Add options from the profiles array
      profiles.forEach(profile => {
        const option = document.createElement("option");
        option.value = profile.name;
        option.textContent = `${profile.name} - $${profile.price}`;
        profileSelect.appendChild(option);
      });
    } else {
      console.error("Profiles not defined. Make sure profiles.js is loaded before customers.js");
      // Add fallback options
      profileSelect.innerHTML = `
        <option value="">-- Select a profile --</option>
        <option value="Basic Plan">Basic Plan - $10</option>
        <option value="Premium Plan">Premium Plan - $20</option>
      `;
    }
  }
});

// Handle customer form submission
const addCustomerForm = document.getElementById("addCustomerForm");
if (addCustomerForm) {
  addCustomerForm.addEventListener("submit", function (e) {
    e.preventDefault();
    
    const data = {
      id: Date.now().toString(),
      name: document.getElementById("name").value,
      phone: document.getElementById("phone").value,
      email: document.getElementById("email").value || '',
      profile: document.getElementById("profile").value,
    };
    
    // Store in localStorage
    const customers = JSON.parse(localStorage.getItem('customers') || '[]');
    customers.push(data);
    localStorage.setItem('customers', JSON.stringify(customers));
    
  // Show success toast
const toast = document.getElementById("toast");
toast.classList.add("show");
setTimeout(() => {
  toast.classList.remove("show");
}, 3000);

    
    // Clear the form
    this.reset();
  });
}
  
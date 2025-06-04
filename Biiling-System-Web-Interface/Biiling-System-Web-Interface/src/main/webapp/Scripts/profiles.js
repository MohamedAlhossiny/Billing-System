// Dummy data for profiles and services
const profiles = [
    { id: 1, name: "Basic Plan", price: 10 },
    { id: 2, name: "Premium Plan", price: 20 },
  ];
  
  const services = [
    { name: "Voice Service", price: 5 },
    { name: "SMS Service", price: 3 },
    { name: "Data Service", price: 8 },
  ];
  
  const servicePackages = [
    { id: 1, name: "Voice Package", type: "recurring", price: 5, includes: ["100 Minutes"] },
    { id: 2, name: "Data Package", type: "recurring", price: 8, includes: ["5GB Data"] },
    { id: 3, name: "SMS Package", type: "recurring", price: 3, includes: ["100 SMS"] },
    { id: 4, name: "Setup Fee", type: "one-time", price: 15, includes: [] }
  ];
  
  // Rate plans for BSCS rating
  const rateConfig = {
    voice: { baseRate: 0.05, // per minute
             rateBlocks: [
               { threshold: 100, rate: 0.04 },
               { threshold: 200, rate: 0.03 }
             ]
           },
    sms: { baseRate: 0.02, // per SMS
             rateBlocks: [
               { threshold: 100, rate: 0.015 },
               { threshold: 200, rate: 0.01 }
             ]
           },
    data: { baseRate: 0.01, // per MB
             rateBlocks: [
               { threshold: 1024, rate: 0.008 }, // 1GB
               { threshold: 5120, rate: 0.005 }  // 5GB
             ]
           }
  };
  
  document.addEventListener("DOMContentLoaded", function () {
    // Check if we're on the profiles page
    const serviceList = document.getElementById("serviceList");
    if (serviceList) {
        // Display existing profiles
        profiles.forEach((profile) => {
            const div = document.createElement("div");
            div.className = "service-item";
            div.innerHTML = `
                <strong>${profile.name}</strong> - $${profile.price}
            `;
            serviceList.appendChild(div);
        });

        // Check if the form exists before adding event listener
        const createProfileForm = document.getElementById("createProfileForm");
        if (createProfileForm) {
            // Handle the form submission to create a new profile
            createProfileForm.addEventListener("submit", function (e) {
                e.preventDefault();
                const profileName = document.getElementById("profileName").value;
                const profilePrice = document.getElementById("profilePrice").value;

                // Add new profile to the list
                const newProfile = { name: profileName, price: parseFloat(profilePrice) };
                profiles.push(newProfile);

                // Refresh the service list
                const div = document.createElement("div");
                div.className = "service-item";
                div.innerHTML = `<strong>${newProfile.name}</strong> - $${newProfile.price}`;
                serviceList.appendChild(div);

                // Clear the form
                createProfileForm.reset();
            });
        }
    }
  });
  
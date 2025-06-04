document.addEventListener("DOMContentLoaded", function() {
  const cdrForm = document.getElementById("cdrUploadForm");
  const fileInput = document.getElementById("cdrFile");
  const customerSelect = document.getElementById("customerSelect");
  
  // Get customers from localStorage
  const customers = JSON.parse(localStorage.getItem('customers') || '[]');
  
  // Populate customer dropdown
  customers.forEach(customer => {
    const option = document.createElement("option");
    option.value = customer.id;
    option.textContent = customer.name;
    customerSelect.appendChild(option);
  });
  
  // Handle form submission
  cdrForm.addEventListener("submit", function(e) {
    e.preventDefault();
    
    const customerId = customerSelect.value;
    const file = fileInput.files[0];
    
    if (!customerId) {
      alert("Please select a customer");
      return;
    }
    
    if (!file) {
      alert("Please select a file");
      return;
    }
    
    // Read file content
    const reader = new FileReader();
    reader.onload = function(e) {
      try {
        // Parse CDR data (assuming CSV format)
        const cdrData = parseCSV(e.target.result);
        
        // Process CDR data according to BSCS rating
        const charges = processCDRData(cdrData, customerId);
        
        // Store charges in localStorage
        const allCharges = JSON.parse(localStorage.getItem('charges') || '[]');
        allCharges.push(...charges);
        localStorage.setItem('charges', JSON.stringify(allCharges));
        
        // Show success message with calculated charges
        alert(`CDR processed successfully. Total charges: $${charges.reduce((sum, charge) => sum + charge.amount, 0).toFixed(2)}`);
        
        // Reset form
        cdrForm.reset();
        
      } catch (error) {
        alert("Error processing CDR: " + error.message);
      }
    };
    
    reader.readAsText(file);
  });
  
  // Parse CSV function
  function parseCSV(text) {
    const lines = text.split('\n');
    const headers = lines[0].split(',');
    const data = [];
    
    for (let i = 1; i < lines.length; i++) {
      if (lines[i].trim() === '') continue;
      
      const values = lines[i].split(',');
      const entry = {};
      
      for (let j = 0; j < headers.length; j++) {
        entry[headers[j].trim()] = values[j].trim();
      }
      
      data.push(entry);
    }
    
    return data;
  }
  
  // Process CDR data using BSCS rating
  function processCDRData(cdrData, customerId) {
    const charges = [];
    
    cdrData.forEach(record => {
      let charge = {
        customerId: customerId,
        date: new Date().toISOString().split('T')[0],
        type: record.type,
        amount: 0,
        details: record
      };
      
      switch (record.type) {
        case 'voice':
          charge.amount = calculateVoiceCharge(parseInt(record.duration));
          break;
        case 'sms':
          charge.amount = calculateSMSCharge(parseInt(record.count));
          break;
        case 'data':
          charge.amount = calculateDataCharge(parseInt(record.MB));
          break;
      }
      
      charges.push(charge);
    });
    
    return charges;
  }
  
  // Calculate charges based on rate configuration
  function calculateVoiceCharge(minutes) {
    return calculateCharge(minutes, rateConfig.voice);
  }
  
  function calculateSMSCharge(count) {
    return calculateCharge(count, rateConfig.sms);
  }
  
  function calculateDataCharge(MB) {
    return calculateCharge(MB, rateConfig.data);
  }
  
  function calculateCharge(units, rateConfig) {
    let charge = 0;
    let remainingUnits = units;
    
    // Find applicable rate block
    let applicableRate = rateConfig.baseRate;
    
    for (let i = rateConfig.rateBlocks.length - 1; i >= 0; i--) {
      if (units > rateConfig.rateBlocks[i].threshold) {
        applicableRate = rateConfig.rateBlocks[i].rate;
        break;
      }
    }
    
    charge = units * applicableRate;
    return parseFloat(charge.toFixed(2));
  }
});
  
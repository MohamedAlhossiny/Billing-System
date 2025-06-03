document.getElementById("loginForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const username = document.getElementById("username").value;
  const password = document.getElementById("password").value;
  const messageBox = document.getElementById("loginMessage");

  if (username === "admin" && password === "admin123") {
    showMessage("Login successful", "success");
    setTimeout(() => {
      window.location.href = "Pages/dashboard.html";
    }, 1500); 
  } else {
    showMessage("Invalid credentials", "error");
  }
});

function showMessage(message, type) {
  const box = document.getElementById("loginMessage");
  box.textContent = message;
  box.className = "message " + type;
}

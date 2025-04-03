document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();
  
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("errorMessage");
  
    // Mock login validation
    if (username === "admin" && password === "12345") {
      alert("Login successful!");
      window.location.href = "proj.html"; // Redirect to the dashboard
    } else {
      errorMessage.textContent = "Invalid username or password!";
    }
  });
  
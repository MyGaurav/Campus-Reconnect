document.addEventListener("DOMContentLoaded", function() {
  const loginForm = document.getElementById("loginForm");
  const usernameInput = document.getElementById("email");
  const passwordInput = document.getElementById("password");
  const message = document.getElementById("message");
  const loginButton = document.getElementById("loginButton");

  const pattern = /^[A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?`~]+$/;


  function validateInputs() {
    let valid = true;
    message.textContent = "";

    if (usernameInput.value.trim() === "" || !pattern.test(usernameInput.value.trim())) {
      valid = false;
    }

    if (passwordInput.value.trim() === "" || !pattern.test(passwordInput.value.trim()) || passwordInput.value.trim().length > 8) {
      valid = false;
    }

    loginButton.disabled = !valid;
  }

  usernameInput.addEventListener("input", validateInputs);
  passwordInput.addEventListener("input", validateInputs);

  // Initial check
  validateInputs();

  // ---------- Form submission ----------
  loginForm.addEventListener("submit", function(event) {

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    if (username === "") {
      message.style.color = "red";
      message.textContent = "❌ Username cannot be empty!";
      return;
    }
    if (!pattern.test(username)) {
      message.style.color = "red";
      message.textContent = "❌ Username contains invalid characters!";
      return;
    }
    if (password === "") {
      message.style.color = "red";
      message.textContent = "❌ Password cannot be empty!";
      return;
    }
    if (!pattern.test(password)) {
      message.style.color = "red";
      message.textContent = "❌ Password contains invalid characters!";
      return;
    }
    if (password.length > 8) {
      message.style.color = "red";
      message.textContent = "❌ Password cannot be more than 8 characters!";
      return;
    }

    // Successful login
    message.style.color = "green";
    message.textContent = "✅ Login Successful!";

    // Mock redirect after 1s
    setTimeout(() => {
      alert("Redirecting to dashboard...");
      // window.location.href = "dashboard.html";
    }, 1000);
  });
});
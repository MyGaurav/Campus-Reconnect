
const form = document.getElementById('signupform');

form.addEventListener('submit', function(e) {
    e.preventDefault();

    // Get the values
    const username = document.getElementById('username').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();

    // Error elements
    const usernameError = document.getElementById('usernameError');
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');
    const confirmPasswordError = document.getElementById('confirmPasswordError'); // corrected ID

    // Clear previous errors
    usernameError.textContent = '';
    emailError.textContent = '';
    passwordError.textContent = '';
    confirmPasswordError.textContent = '';

    let valid = true;

    // Username validation
    if(username === ''){
        usernameError.textContent = 'Username is required';
        valid = false;
    } else if (username.length < 6){
        usernameError.textContent = 'Username must be at least 6 characters';
        valid = false;
    }

    // Email validation
    const emailPattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/;
    if(email === ''){
        emailError.textContent = "Email is required";
        valid = false;
    } else if(!email.match(emailPattern)){
         emailError.textContent = "Email should be in email format";
         valid = false;
    }

    // Password validation
    if(password === ''){
        passwordError.textContent = 'Password is required';
        valid = false;
    } else if(password.length < 8){
        passwordError.textContent = 'Password must be at least 8 characters';
        valid = false;
    }

    // Confirm password validation
    if(confirmPassword === ''){
        confirmPasswordError.textContent = 'Please confirm your password';
        valid = false;
    } else if(confirmPassword !== password){
        confirmPasswordError.textContent = 'Passwords do not match';
        valid = false;
    }

    // If valid
    if(valid){
        alert('Signup Successful');
        form.reset();
    }
});


// ----Toggle Show/Hide Password ----
function togglePassword(id, el){
    const input = document.getElementById(id);
    if (input.type === 'password'){
        input.type = 'text';
        el.textContent = '';
    } else {
        input.type = 'password';
        el.textContent = '';
    }
}


// ---- Typing Animation for Slogan ----
const sloganElement = document.querySelector('.slogan');
const slogans = ["Connecting Everyone", "Find lost things", "Find, Share, Connect"];
let index = 0;
let charIndex = 0;
let typingSpeed = 100;
let erasingSpeed = 50;
let delayBetween = 1500;

function type() {
  if (charIndex < slogans[index].length) {
    sloganElement.textContent += slogans[index].charAt(charIndex);
    charIndex++;
    setTimeout(type, typingSpeed);
  } else {
    setTimeout(erase, delayBetween);
  }
}

function erase() {
  if (charIndex > 0) {
    sloganElement.textContent = slogans[index].substring(0, charIndex - 1);
    charIndex--;
    setTimeout(erase, erasingSpeed);
  } else {
    index = (index + 1) % slogans.length;
    setTimeout(type, typingSpeed);
  }
}

// Start typing effect on page load
document.addEventListener("DOMContentLoaded", function() {
  setTimeout(type, 500);
});
/**
 * ShelfIt Bookmarking Application
 * Client-side JavaScript functionality
 */

// DOM Ready Event Listener
document.addEventListener("DOMContentLoaded", function () {
  // Email validation functionality
  setupEmailValidation();

  // Form validation
  setupFormValidation();

  // View toggle functionality (list vs grid view)
  setupViewToggle();

  // Navigation functionality
  setupNavigation();

  // Rating stars functionality
  setupRatingStars();
});

/**
 * Sets up email validation for login and signup forms
 */
function setupEmailValidation() {
  // Email validation for login form
  const loginEmail = document.getElementById("email");
  if (loginEmail) {
    loginEmail.addEventListener("input", function () {
      validateEmail(loginEmail, "login-email-error");
    });
  }

  // Email validation for signup form
  const signupEmail = document.getElementById("signup_email");
  if (signupEmail) {
    signupEmail.addEventListener("input", function () {
      validateEmail(signupEmail, "signup-email-error");
    });
  }
}

/**
 * Validates an email input element
 * @param {HTMLElement} emailInput - The email input element
 * @param {string} errorElementId - ID of the error message element
 */
function validateEmail(emailInput, errorElementId) {
  const errorElement = document.getElementById(errorElementId);
  if (!errorElement) return;

  // Regex pattern for email validation
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  if (emailInput.value.trim() === "") {
    errorElement.innerText = "";
  } else if (!emailPattern.test(emailInput.value)) {
    errorElement.innerText = "Please enter a valid email address";
  } else {
    errorElement.innerText = "";
  }
}

/**
 * Sets up form validation for login and signup forms
 */
function setupFormValidation() {
  // Login form validation
  const loginForm = document.getElementById("login-form");
  if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
      if (!validateLoginForm()) {
        e.preventDefault();
      }
    });
  }

  // Signup form validation
  const signupForm = document.getElementById("signup-form");
  if (signupForm) {
    signupForm.addEventListener("submit", function (e) {
      if (!validateSignupForm()) {
        e.preventDefault();
      }
    });
  }
}

/**
 * Validates the login form
 * @returns {boolean} Whether the form is valid
 */
function validateLoginForm() {
  const email = document.getElementById("email");
  const password = document.getElementById("password");

  if (!email || !password) return false;

  if (email.value.trim() === "" || password.value.trim() === "") {
    alert("Please enter your email address and password!");
    return false;
  }

  // Email pattern validation
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (!emailPattern.test(email.value)) {
    alert("Please enter a valid email address!");
    return false;
  }

  return true;
}

/**
 * Validates the signup form
 * @returns {boolean} Whether the form is valid
 */
function validateSignupForm() {
  const email = document.getElementById("signup_email");
  const password = document.getElementById("signup_password");
  const firstName = document.getElementById("signup_firstName");
  const lastName = document.getElementById("signup_lastName");

  if (!email || !password || !firstName || !lastName) return false;

  if (
    email.value.trim() === "" ||
    password.value.trim() === "" ||
    firstName.value.trim() === "" ||
    lastName.value.trim() === ""
  ) {
    alert("Please enter all required fields!");
    return false;
  }

  // Email pattern validation
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (!emailPattern.test(email.value)) {
    alert("Please enter a valid email address!");
    return false;
  }

  // Name validation (only letters, spaces, hyphens, and apostrophes)
  const namePattern = /^[A-Za-z\s\-']+$/;
  if (!namePattern.test(firstName.value) || !namePattern.test(lastName.value)) {
    alert("Names can only contain letters, spaces, hyphens, and apostrophes!");
    return false;
  }

  return true;
}

/**
 * Sets up view toggle functionality (list vs grid)
 */
function setupViewToggle() {
  const listViewBtn = document.getElementById("list-view-btn");
  const gridViewBtn = document.getElementById("grid-view-btn");
  const booksContainer = document.getElementById("books-container");

  if (!listViewBtn || !gridViewBtn || !booksContainer) return;

  listViewBtn.addEventListener("click", function () {
    booksContainer.classList.remove("grid-view");
    booksContainer.classList.add("list-view");
    listViewBtn.classList.add("active");
    gridViewBtn.classList.remove("active");

    // Store user preference
    localStorage.setItem("viewPreference", "list");
  });

  gridViewBtn.addEventListener("click", function () {
    booksContainer.classList.remove("list-view");
    booksContainer.classList.add("grid-view");
    gridViewBtn.classList.add("active");
    listViewBtn.classList.remove("active");

    // Store user preference
    localStorage.setItem("viewPreference", "grid");
  });

  // Apply saved preference, defaulting to list view
  const savedView = localStorage.getItem("viewPreference") || "list";
  if (savedView === "grid") {
    gridViewBtn.click();
  } else {
    listViewBtn.click();
  }
}

/**
 * Sets up navigation functionality
 */
function setupNavigation() {
  const navLinks = document.querySelectorAll(".nav-link");

  navLinks.forEach((link) => {
    link.addEventListener("click", function (e) {
      // Remove active class from all links
      navLinks.forEach((navLink) => {
        navLink.classList.remove("active");
      });

      // Add active class to clicked link
      this.classList.add("active");
    });
  });
}

/**
 * Sets up dynamic rating stars functionality
 */
function setupRatingStars() {
  const ratingContainers = document.querySelectorAll(".book-rating");

  ratingContainers.forEach((container) => {
    const ratingValue = parseFloat(container.getAttribute("data-rating") || 0);
    const maxRating = 5;
    let starsHTML = "";

    for (let i = 1; i <= maxRating; i++) {
      if (i <= ratingValue) {
        starsHTML += '<span class="star star-filled">★</span>';
      } else if (i - 0.5 <= ratingValue) {
        starsHTML += '<span class="star star-half">★</span>';
      } else {
        starsHTML += '<span class="star star-empty">★</span>';
      }
    }

    const starsElement = container.querySelector(".rating-stars");
    if (starsElement) {
      starsElement.innerHTML = starsHTML;
    }
  });
}

/**
 * Handles adding a book via URL
 * @param {Event} event - The form submission event
 */
function handleAddBook(event) {
  event.preventDefault();

  const bookUrlInput = document.getElementById("book-url");
  if (!bookUrlInput) return;

  const bookUrl = bookUrlInput.value.trim();
  if (bookUrl === "") {
    alert("Please enter a valid book URL");
    return;
  }

  // Check if URL is from Goodreads
  if (!bookUrl.includes("goodreads.com")) {
    alert("Please enter a valid Goodreads book URL");
    return;
  }

  // Submit the form
  event.target.submit();
}

/**
 * Handles saving a book to the user's collection
 * @param {number} bookId - The ID of the book to save
 */
function saveBook(bookId) {
  // Redirect to the save endpoint
  window.location.href = `/bookmark/save?bid=${bookId}`;
}

/**
 * Handles removing a book from the user's collection
 * @param {number} bookId - The ID of the book to remove
 */
function removeBook(bookId) {
  if (
    confirm("Are you sure you want to remove this book from your collection?")
  ) {
    // Redirect to the remove endpoint
    window.location.href = `/bookmark/remove?bid=${bookId}`;
  }
}

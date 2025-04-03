// Switch between tabs (Profile Info, Order History, Settings)
function showTab(tabId) {
  const tabs = document.querySelectorAll('.tab-content');
  tabs.forEach(tab => tab.classList.remove('active'));

  const activeTab = document.getElementById(tabId);
  activeTab.classList.add('active');
}

// Simulate changing profile picture (can be implemented with file upload)
function changeProfilePicture() {
  const profilePic = document.getElementById('profile-pic');
  const newPic = prompt('Enter the URL of your new profile picture:');
  if (newPic) {
    profilePic.src = newPic;
  }
}

// Simulate editing profile
function editProfile() {
  const name = prompt('Enter your full name:', document.getElementById('user-name').innerText);
  const email = prompt('Enter your email:', document.getElementById('user-email').innerText);

  if (name) {
    document.getElementById('user-name').innerText = name;
  }
  if (email) {
    document.getElementById('user-email').innerText = email;
  }
}

// Save settings after updating profile
function saveSettings() {
  const name = document.getElementById('name').value;
  const email = document.getElementById('email').value;
  const phone = document.getElementById('phone').value;

  document.getElementById('profile-full-name').innerText = name;
  document.getElementById('profile-email').innerText = email;
  document.getElementById('profile-phone').innerText = phone;

  alert('Profile updated successfully!');
}

// ✅ Fetch user details from backend and update UI
async function fetchProfileDetails() {
  try {
    const response = await fetch('http://localhost:8080/inventory-management/getUser'); // Match your servlet path
    if (!response.ok) throw new Error('Failed to fetch user details');
    const data = await response.json();

    // ✅ Update the profile fields with fetched data
    document.getElementById('user-name').innerText = data.name || 'N/A';
    document.getElementById('user-email').innerText = data.email || 'N/A';
    document.getElementById('profile-full-name').innerText = data.name || 'N/A';
    document.getElementById('profile-email').innerText = data.email || 'N/A';
    document.getElementById('profile-phone').innerText = data.phone || 'N/A';
    document.getElementById('name').value = data.name || '';
    document.getElementById('email').value = data.email || '';
    document.getElementById('phone').value = data.phone || '';

  } catch (error) {
    console.error('Error fetching user info:', error);
  }
}

// ✅ Call the function when the page loads
window.onload = () => {
  fetchProfileDetails();
};

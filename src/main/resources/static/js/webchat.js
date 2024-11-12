let currentUser = null;

// Function to load the discussion with a specific user
function openChat(userEmail) {
    currentUser = userEmail;

    // Set chat header to the user's name (this could be enhanced with server-side data)
    document.getElementById('chat-header').textContent = 'Chat with ' + userEmail;

    // Fetch discussion messages from the server (for simplicity, let's use a dummy data here)
    loadMessages(userEmail);
}

// Function to load messages for a specific discussion (current user with another user)
function loadMessages(userEmail) {
    const chatMessagesContainer = document.getElementById('chat-messages');
    chatMessagesContainer.innerHTML = ''; // Clear previous messages

    // Example of a loaded discussion (this would be replaced by actual API call)
    const messages = [
        { sender: 'Me', content: 'Hello!', time: '10:00 AM' },
        { sender: userEmail, content: 'Hi there!', time: '10:05 AM' },
    ];

    messages.forEach(msg => {
        const messageDiv = document.createElement('div');
        messageDiv.classList.add('message');
        messageDiv.innerHTML = `<strong>${msg.sender}</strong>: ${msg.content} <span class="time">(${msg.time})</span>`;
        chatMessagesContainer.appendChild(messageDiv);
    });
}

// Function to send a message in the current chat (replace with actual API request)
function sendMessage() {
    const messageInput = document.getElementById('message-input');
    const messageText = messageInput.value.trim();

    if (messageText === '' || !currentUser) return;

    const chatMessagesContainer = document.getElementById('chat-messages');
    const newMessageDiv = document.createElement('div');
    newMessageDiv.classList.add('message');
    newMessageDiv.innerHTML = `<strong>Me</strong>: ${messageText} <span class="time">(Just now)</span>`;
    chatMessagesContainer.appendChild(newMessageDiv);

    // Simulate sending the message (e.g., via an API)
    messageInput.value = ''; // Clear the input
}

// Function to send a public message to all users
function sendPublicMessage() {
    const publicMessageInput = document.getElementById('public-message-input');
    const publicMessageText = publicMessageInput.value.trim();

    if (publicMessageText === '') return;

    const publicChatMessagesContainer = document.getElementById('public-chat-messages');
    const newPublicMessageDiv = document.createElement('div');
    newPublicMessageDiv.classList.add('message');
    newPublicMessageDiv.innerHTML = `<strong>Broadcast</strong>: ${publicMessageText} <span class="time">(Just now)</span>`;
    publicChatMessagesContainer.appendChild(newPublicMessageDiv);

    // Simulate broadcasting the message (e.g., via an API)
    publicMessageInput.value = ''; // Clear the input
}

let stompClient = null;
let currentReceiverId = null;

function connect() {
    // Establish connection with SockJS and STOMP
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected to WebSocket:', frame);

        if (currentReceiverId) {
            subscribeToPrivateChat(currentReceiverId);
        }
    }, function (error) {
        console.error('WebSocket connection error:', error);
    });
}

function subscribeToPrivateChat(receiverId) {
    currentReceiverId = receiverId;

    // Unsubscribe from any previous private chats before subscribing to the new one
    if (stompClient && stompClient.connected) {
        console.log(`Subscribing to private chat topic: /topic/private/${receiverId}`);

        stompClient.subscribe(`/topic/private/${receiverId}`, function (message) {
            showMessage(JSON.parse(message.body));
        });
    } else {
        console.warn('WebSocket is not connected. Unable to subscribe.');
    }
}

function sendPrivateMessage() {
    const messageContent = document.getElementById("private-message").value;
    
    if (messageContent && currentReceiverId) {
        console.log(`Sending private message to receiver ${currentReceiverId}:`, messageContent);

        const message = {
            messageContent: messageContent,
            receiver: { id: currentReceiverId }
        };

        stompClient.send("/app/chat.private", {}, JSON.stringify(message));

        // Clear the input field after sending
        document.getElementById("private-message").value = '';
    } else {
        console.warn("Message content is empty or receiver ID is not set.");
    }
}

function showMessage(message) {
    const chatContent = document.getElementById("private-messages");
    chatContent.innerHTML += `<div><b>${message.sender.firstName}:</b> ${message.messageContent}</div>`;
}

window.addEventListener('load', function () {
    connect();

    // Assume currentReceiverId is set dynamically on page load or by an action in the app
    // For instance, simulate this here as if you're setting it based on URL or user action.
    // Set the currentReceiverId manually if not set by other means:
    currentReceiverId = getReceiverIdFromUrlOrContext();
    if (currentReceiverId) {
        subscribeToPrivateChat(currentReceiverId);
    } else {
        console.warn("No receiver ID set for private chat.");
    }
});

function getReceiverIdFromUrlOrContext() {
    // Implement this function to extract or set the receiver ID, e.g., based on URL or app state
    return /* receiverId or logic to determine it */;
}

// Establish a STOMP connection over the WebSocket
let socket = new SockJS('/ws'); // Use SockJS if configured in Spring for compatibility
let stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // Subscribe to the public chat topic
    stompClient.subscribe('/topic/broadcast', function (message) {
        displayMessage(JSON.parse(message.body));
    });
});

function sendPublicMessage() {
    if (stompClient && stompClient.connected) {
        // Construct a msg object containing the data the server needs to process the message from the chat client.
        const msg = {
            messageContent: document.getElementById("message").value,
        };

        // Send the message to the broadcast channel via STOMP
        stompClient.send("/app/chat.broadcast", {}, JSON.stringify(msg));

        // Clear the input field after sending the message
        document.getElementById("message").value = "";
    } else {
        console.error("WebSocket connection is not open.");
    }
}

// Display the received message in the chat area 
function displayMessage(message) {
    let messageElem = document.createElement('div');
    
    // Use innerHTML to allow HTML tags to be rendered inside the message element
    messageElem.innerHTML = `<p><strong>${message.sender.firstName} ${message.sender.lastName}</strong>: ${message.messageContent}</p>`;
    
    // Append the message element to the conversation
    document.getElementById('conversation').appendChild(messageElem);
}

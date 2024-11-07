let stompClient = null;

function connect() {
    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // Subscribe to broadcast messages
        stompClient.subscribe('/topic/broadcast', function (message) {
            showMessage(JSON.parse(message.body), "broadcast");
        });

        // Subscribe to private messages
        stompClient.subscribe('/topic/private', function (message) {
            showMessage(JSON.parse(message.body), "private");
        });
    });
}

function sendMessage() {
    const from = document.getElementById("from").value;
    const to = document.getElementById("to").value;
    const text = document.getElementById("text").value;
    if (from && text) {
        const message = { 'from': from, 'text': text, 'to': to };

        // Send to the correct endpoint based on whether it's a private message or a broadcast
        if (to) {
            stompClient.send("/app/chat.private", {}, JSON.stringify(message));
        } else {
            stompClient.send("/app/chat.broadcast", {}, JSON.stringify(message));
        }

        document.getElementById("text").value = ''; // Clear the message input
    }
}

function showMessage(message, type) {
    const messageElem = document.createElement('div');
    messageElem.textContent = `${message.time} ${message.from}: ${message.text}`;

    if (type === "broadcast") {
        document.getElementById("broadcast-messages").appendChild(messageElem);
    } else if (type === "private") {
        document.getElementById("private-messages").appendChild(messageElem);
    }
}

// Connect on page load
window.onload = connect;
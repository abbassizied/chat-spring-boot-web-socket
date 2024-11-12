let stompClient = null;

function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/public', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendPublicMessage() {
    let message = document.getElementById("message-input").value;
    stompClient.send("/app/chat.broadcast", {}, JSON.stringify({ 'messageContent': message }));
}

function showMessage(message) {
    let chatContent = document.getElementById("chat-content");
    chatContent.innerHTML += `<div>${message.sender.firstName}: ${message.messageContent}</div>`;
}

connect();

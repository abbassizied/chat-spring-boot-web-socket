let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        document.getElementById("messages").innerHTML += "<p>Connection opened</p>";

        // Subscribe to the scoreboard topic
        stompClient.subscribe('/topic/scoreboard', (message) => {
            document.getElementById("messages").innerHTML += `<p>Data received: ${message.body}</p>`;
        });
    });
}

function sendMessage() {
    if (stompClient && stompClient.connected) {
        stompClient.send("/app/scoreboard", {}, "Hi server, please send me the score of yesterday's game");
    }
}

function disconnect() {
    if (stompClient) {
        stompClient.disconnect();
        document.getElementById("messages").innerHTML += "<p>Connection closed</p>";
    }
}
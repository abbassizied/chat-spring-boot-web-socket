let stompClient = null;
let socket = new SockJS("/ws");
stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log("Connected: " + frame);
    stompClient.subscribe("/topic/private", function (messageOutput) {
        let message = JSON.parse(messageOutput.body);
		// console.log("server send: " + message);
        displayMessage(message); // Display the received message
    });
});



function sendPrivateMessage() {
    let receiverId = document.getElementById("receiverId").value;  // Assuming you have an input field with the receiver's ID.
    let messageContent = document.getElementById("messageContent").value;
    
    // Prepare the message payload
    let message = {
        messageContent: messageContent
    };

    // Send the message to the WebSocket destination
    stompClient.send("/app/chat.private." + receiverId, {}, JSON.stringify(message));

    // Clear the message input field after sending
    document.getElementById("messageContent").value = "";
}




function displayMessage(message) {
	// console.log("Begin - displayMessage");
    let messageElem = document.createElement("div");
    messageElem.innerHTML = `<p><strong>${message.sender.firstName} ${message.sender.lastName}</strong>: ${message.messageContent}</p>`;
    document.getElementById("conversation").appendChild(messageElem);
	// console.log("End - displayMessage");
}





# websockets
 
- [WebRTC](https://webrtc.org/)
- [WebTorrent](https://webtorrent.io/)
- [HTTP Live Streaming (HLS)](https://en.wikipedia.org/wiki/HTTP_Live_Streaming)
---
- [WebSocket](https://javascript.info/websocket)
- [Server Sent Events](https://javascript.info/server-sent-events)
- [Sending Messages](https://docs.spring.io/spring-framework/reference/web/websocket/stomp/handle-send.html)
- [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket)
- [A sample demonstrating capabilities in the Spring Framework to build WebSocket-style messaging applications](https://github.com/rstoyanchev/spring-websocket-portfolio): The application uses STOMP (over WebSocket) for messaging between browsers and server and SockJS for WebSocket fallback options.
---
- [sockjs-client library](https://github.com/sockjs/sockjs-client)
- [Javascript and Typescript Stomp client for Web browsers and node.js apps](https://github.com/stomp-js/stompjs)
- [WebSocket emulation - Javascript client](https://github.com/sockjs/sockjs-client)
- [StompJs Family](https://stomp-js.github.io/)
---
- [Index of /spring-framework/reference/web/websocket](https://docs.spring.io/spring-framework/reference/web/websocket/)
- [Index of /spring-framework/reference/web/websocket/stomp](https://docs.spring.io/spring-framework/reference/web/websocket/stomp/)
---
- [wiki/WebSocket](https://en.wikipedia.org/wiki/WebSocket)
- [mozilla - The WebSocket API (WebSockets)](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API)
- [mozilla - Writing a WebSocket server in Java](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_a_WebSocket_server_in_Java)
- [mozilla - Writing WebSocket client applications](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_client_applications)

## overview
 
- **WebSocket** is a communication **protocol** which features **bi-directional**, **full-duplex** communication over a persistent TCP connection.
- WebSocket was standardized as a communication protocol by IETF as [RFC 6455](https://datatracker.ietf.org/doc/html/rfc6455) in 2011. Most modern web browsers today support the WebSocket protocol.
- **WebSocket does not put any condition on the message to be exchanged.** 
- There are many popular **subprotocols** like **STOMP** available for use. STOMP stands for **Simple Text Oriented Messaging Protocol** and works over WebSocket. 
- You can use SockJS client to support browsers that do not support WebSockets natively.
- You would need to consider the following:
	- URL protocol conventions are different for WebSockets (ws:/wss:) and SockJS (http: or https:).
	- Internal handshake sequences are different — so, some brokers will use different end points for both protocols.
	- Neither of these allows custom headers to be set during the HTTP handshake.
	- SockJS internally supports different transport mechanisms. You might face specific limitations depending on actual transport in use.
	- Auto reconnect is not quite reliable with SockJS.
	- Heartbeats may not be supported over SockJS by some brokers.
	- SockJS does not allow more than one simultaneous connection to the same broker. This usually is not a problem for most of the applications.
 
## Spring WebSockets

- How to broadcast every message to every user
- How to send these messages in a secure way
 
## Writing WebSocket client applications

- WebSocket client applications use the [WebSocket API](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API) to communicate with [WebSocket servers](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers) using the WebSocket protocol.

- Here’s an example:
```js
// Creating a WebSocket object
// The WebSocket constructor accepts one required and one optional parameter:
const ws = new WebSocket("ws://localhost:8080");

// Sending data to the server
// The open event is fired when a connection with a WebSocket is opened.
ws.onopen = (event) => {
  ws.send("Here's some text that the server is urgently awaiting!");
}; 

// Receiving messages from the server
// The message event is fired when data is received through a WebSocket.
ws.onmessage = (event) => {
  console.log(event.data);
};

// Closing the connection
ws.close();
``` 

- **Using JSON to transmit objects**: One handy thing you can do is use JSON to send reasonably complex data to the server. For example, a chat program can interact with a server using a protocol implemented using packets of JSON-encapsulated data:
```js
// Send text to all users through the server
function sendText() {
  // Construct a msg object containing the data the server needs to process the message from the chat client.
  const msg = {
    type: "message",
    text: document.getElementById("text").value,
    id: clientID,
    date: Date.now(),
  };

  // Send the msg object as a JSON-formatted string.
  ws.send(JSON.stringify(msg));

  // Blank the text input element, ready to receive the next line of text from the user.
  document.getElementById("text").value = "";
} 
```

- **Receiving and interpreting JSON objects**: The code that interprets these incoming messages might look like this:
```js
exampleSocket.onmessage = (event) => {
  const f = document.getElementById("chat-box").contentDocument;
  let text = "";
  const msg = JSON.parse(event.data);
  const time = new Date(msg.date);
  const timeStr = time.toLocaleTimeString();

  switch (msg.type) {
    case "id":
      clientID = msg.id;
      setUsername();
      break;
    case "username":
      text = `User <em>${msg.name}</em> signed in at ${timeStr}<br>`;
      break;
    case "message":
      text = `(${timeStr}) ${msg.name} : ${msg.text} <br>`;
      break;
    case "reject-username":
      text = `Your username has been set to <em>${msg.name}</em> because the name you chose is in use.<br>`;
      break;
    case "user-list":
      document.getElementById("user-list-box").innerText = msg.users.join("\n");
      break;
  }

  if (text.length) {
    f.write(text);
    document.getElementById("chat-box").contentWindow.scrollByPages(1);
  }
};
```
 
## Writing WebSocket server applications

- **Enable STOMP**:
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry; 
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
		// Use the built-in message broker for subscriptions and broadcasting and route
		// messages whose destination header begins with /topic or /queue to the broker.
        config.enableSimpleBroker("/topic", "/queue"); // Prefix for server-side broadcasts
        
		// STOMP messages whose destination header begins with /app are routed to
		// @MessageMapping methods in @Controller classes.
        config.setApplicationDestinationPrefixes("/app"); // Prefix for client-side messages
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		// /ws is the HTTP URL for the endpoint to which a WebSocket (or SockJS) client
		// needs to connect for the WebSocket handshake.
        registry.addEndpoint("/ws").withSockJS(); // Enables WebSocket endpoint with SockJS fallback
        registry.setPreserveReceiveOrder(true); // To enable ordered publishing
    } 
    
}
``` 
- **Note**: For the built-in simple broker, the ```/topic``` and ```/queue``` prefixes do not have any special meaning. They are merely a convention to differentiate between **pub-sub** versus **point-to-point** messaging (that is, many subscribers versus one consumer). When you use an external broker, check the STOMP page of the broker to understand what kind of STOMP destinations and prefixes it supports.
- To connect from a browser, for STOMP, you can use [stomp-js/stompjs](https://github.com/stomp-js/stompjs) which is the most actively maintained JavaScript library. The following example code is based on it:
```java
const stompClient = new StompJs.Client({
       brokerURL: 'ws://domain.com/ws',
       onConnect: () => {
           // ...
       }
   });
``` 
- Create a **Controller**
```java
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

}
``` 
- The **@MessageMapping** annotation ensures that, if a message is sent to the ```/hello``` destination, the greeting() method is called.
- The return value is broadcast to all subscribers of ```/topic/greetings```, as specified in the **@SendTo** annotation. 

- You can use **@MessageMapping** to annotate methods that route messages based on their destination.
- By default, the mapping values are Ant-style path patterns (for example ```/thing*```, ```/thing/**```), including support for template variables (for example, ```/thing/{id}```). The values can be referenced through **@DestinationVariable** method arguments.
- When messages are routed to @MessageMapping methods, they are matched with AntPathMatcher.
- By default, patterns are expected to use slash (/) as the separator.
- This is a good convention in web applications and similar to HTTP URLs.
- However, if you are more used to messaging conventions, you can switch to using dot (.) as the separator.
- The following example shows **how to do so in Java configuration**:
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// ...

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setPathMatcher(new AntPathMatcher("."));
		registry.enableStompBrokerRelay("/queue", "/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}
}
``` 
- **After that, a controller can use a dot (.) as the separator in @MessageMapping methods, as the following example shows**:
```java
@Controller
@MessageMapping("red")
public class RedController {

	@MessageMapping("blue.{green}")
	public void handleGreen(@DestinationVariable String green) {
		// ...
	}
}
``` 
- The client can now send a message to ```/app/red.blue.green123```.


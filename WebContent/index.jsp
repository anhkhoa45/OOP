<!DOCTYPE html>
<html>
<head>
    <title>Chat Room</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        * {
            margin: auto;
            padding: 0px;
        }

        html, body {
            width:90%;
            height:90%;
            background: #eeeeda;
        }

        .panel {
            border: 2px solid #0078ae;
            border-radius: 5px;
            width:100%;
        }

        .message-area {
            height: 70%;
            resize: none;
            box-sizing: border-box;
        }

        .input-area {
            background: #0078ae;
            box-shadow: inset 0 0 10px #00568c;
        }

        .input-area input {
            margin: 0.5em 0em 0.5em 0.5em;
        }

        .text-field {
            border: 1px solid grey;
            padding: 0.2em;
            box-shadow: 0 0 5px #000000;
        }

        .button {
            border: none;
            padding: 5px 5px;
            border-radius: 5px;
            width: 60px;
            background: orange;
            box-shadow: inset 0 0 10px #000000;
            font-weight: bold;
        }

        .button:hover {
            background: yellow;
        }

        h1 {
            font-size: 1.5em;
            padding: 5px;
            margin: 5px;
        }

        #userName {
            width: 20%;
        }

        #messageInput {
            min-width: 60%;
            max-width: 80%;
        }
    </style>
</head>
<body onload="connect();" onunload="disconnect();">
<h1> Chat Room </h1>
<textarea id="messages" class="panel message-area" disabled="true"></textarea>
<div class="panel input-area">
    <input id="userName" class="text-field" type="text" placeholder="Name"/>
    <input id="messageInput" class="text-field" type="text" placeholder="Message"
           onkeydown="if (event.keyCode == 13) sendMessage();" />
    <input class="button" type="submit" value="Send" onclick="sendMessage();" />
</div>
<script>
    var endPointURL = "ws://" + window.location.host + "/chat";

    var chatClient = null;

    function connect () {
        chatClient = new WebSocket(endPointURL);
        chatClient.onmessage = function (event) {
            var messagesArea = document.getElementById("messages");
            var jsonObj = JSON.parse(event.data);
            var message = jsonObj.user + ": " + jsonObj.message + "\r\n";
            messagesArea.value = messagesArea.value + message;
            messagesArea.scrollTop = messagesArea.scrollHeight;
        };
    }

    function disconnect () {
        chatClient.close();
    }

    function sendMessage() {
        var user = document.getElementById("userName").value.trim();
        if (user === "")
            alert ("Please enter your name!");

        var inputElement = document.getElementById("messageInput");
        var message = inputElement.value.trim();
        if (message !== "") {
            var jsonObj = {"user" : user, "message" : message};
            chatClient.send(JSON.stringify(jsonObj));
            inputElement.value = "";
        }
        inputElement.focus();
    }
</script>
</body>
</html>
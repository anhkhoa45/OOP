<!DOCTYPE html>
<html>
<head>
    <title>Chat Room</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body onload="connect();" onunload="disconnect();">
<h1>Game box</h1>
<p id="question"></p>
<textarea id="answers" class="panel message-area" disabled="true"></textarea>
<div class="panel input-area">
    <input id="messageInput" class="text-field" type="text" placeholder="Your answer"
           onkeydown="if (event.keyCode == 13) sendMessage();" />
    <input class="button" type="submit" value="Send" onclick="sendMessage();" />
</div>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</body>
</html>
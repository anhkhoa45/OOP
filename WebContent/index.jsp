<!DOCTYPE html>
<html>
<head>
    <title>Chat Room</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body onunload="disconnect();">

<jsp:include page="./components/login.jsp"></jsp:include>

<jsp:include page="./components/welcome_screen.jsp"></jsp:include>

<jsp:include page="./components/game_box.jsp"></jsp:include>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/socket.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
</body>
</html>
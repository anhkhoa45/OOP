<!DOCTYPE html>
<html>
<head>
    <title>Chat Room</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body onunload="disconnect();">

<div class="container margin-top-50">
<jsp:include page="./components/login.jsp"></jsp:include>

<jsp:include page="./components/welcome_screen.jsp"></jsp:include>

<jsp:include page="./components/game_box.jsp"></jsp:include>
</div>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/jquery-3.3.1.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/socket.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/login.js"></script>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
    <title>Chat Room</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>

<form>
    Ten: <input type="text" id="nameInp">
    <button type="button" onclick="send()">Send</button>
</form>

<script>
    axios.get('/api/hello')
        .then(function (response) {
            console.log(response);
        });

    function send(){
        var name = document.getElementById('nameInp').value;
        axios({
            method: 'POST',
            url: '/api/hello/1',
            headers: {
                'content-type': "application/x-www-form-urlencoded",
            },
            data: {name}
        })
            .then(function (response) {
                console.log(response);
            });
    }
</script>
</body>
</html>
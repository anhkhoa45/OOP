let endPointURL = "ws://" + window.location.host + "/game-server/1";

let chatClient = null;

function connect() {
    chatClient = new WebSocket(endPointURL);
    chatClient.onmessage = function (event) {
        let messagesArea = document.getElementById("answers");
        let jsonObj = JSON.parse(event.data);

        if(jsonObj.status === 200){
            switch (jsonObj.action) {
                case 0: // Game created
                    messagesArea.value += "Connected";
                    break;
                case 1: // Game joined
                    break;
                case 2: // Answer sent
                    messagesArea.value += `${jsonObj.content.answer} - Score: ${jsonObj.content.score} \n`;
                    break;
            }
        }
        console.log(jsonObj);
    };
}

function disconnect() {
    chatClient.close();
}

function sendMessage() {
    let inputElement = document.getElementById("messageInput");
    let message = inputElement.value.trim();
    if (message !== "") {
        let jsonObj = {
            status: 200,
            action: 2,
            content: {
                answer: message
            }
        };
        chatClient.send(JSON.stringify(jsonObj));
        inputElement.value = "";
    }
    inputElement.focus();
}

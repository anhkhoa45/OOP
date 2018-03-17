let endPointURL = "ws://" + window.location.host + "/game-server/1";

let chatClient = null;

function connectSocket() {
    chatClient = new WebSocket(endPointURL);
    chatClient.onmessage = onMessage;

    chatClient.onopen = requestListGame;
}

function disconnect() {
    chatClient.close();
}

function sendMessage() {
    let inputElement = document.getElementById("messageInput");
    let message = inputElement.value;
    if (message !== "") {
        let jsonObj = {
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

function onMessage(event) {
    let messagesArea = document.getElementById("answers");
    let jsonObj = JSON.parse(event.data);

    if(jsonObj.status === 200){
        switch (jsonObj.action) {
            case 0: // Game created
                messagesArea.value += "Connected";
                document.getElementById('welcomeScreen').classList.remove('active');
                document.getElementById('gameBox').classList.add('active');
                break;
            case 1: // Game joined
                break;
            case 2: // Answer sent
                messagesArea.value += `${jsonObj.content.answer} - Score: ${jsonObj.content.score} \n`;
                break;
            case 3:
                let listGame = document.getElementById('listGame');
                let games = jsonObj.content;
                let el;
                Object.values(games).forEach(game => {
                    el = document.createElement('li');
                    el.innerHTML = `${game.id} - ${game.playerCount}/2`;
                    listGame.appendChild(el);
                });
                break;
        }
    }
}

function requestListGame(){
    chatClient.send(JSON.stringify({ action: 3 }));
}

function createNewGame(){
    chatClient.send(JSON.stringify({ action: 0}));
}
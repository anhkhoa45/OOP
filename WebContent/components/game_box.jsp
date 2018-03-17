<section id="gameBox">
    <h1>Game box</h1>
    <p id="question"></p>
    <textarea id="answers" class="panel message-area" disabled="true"></textarea>
    <div class="panel input-area">
        <input id="messageInput" class="text-field" type="text" placeholder="Your answer"
               onkeydown="if (event.keyCode === 13) sendMessage();" />
        <input class="button" type="submit" value="Send" onclick="sendMessage();" />
    </div>
</section>
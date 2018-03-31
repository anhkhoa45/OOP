<section id="welcomeScreen" class="row">
    <div class="col-md-8">
        <div class="row">
            <div class="col-md-12">
                <h1>Welcome screen</h1>
            </div>
            <div class="col-md-4">
                <button type="button" class="btn btn-primary btn-lg btn-block"
                        onclick="createNewGame()">
                    New Game
                </button>
            </div>
            <div class="col-md-4">
                <button type="button" class="btn btn-info btn-lg btn-block">List games</button>
            </div>
            <div class="col-md-4">
                <button type="button" class="btn btn-danger btn-lg btn-block">Random game</button>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <h3>Online players</h3>
        <ul id="listPlayers">
        </ul>
    </div>
</section>
class Game {
  constructor({id, master, guest, mode, status, topic}) {
    this.id = id;
    this.master = master || null;
    this.guest = guest || null;
    this.mode = mode || null;
    this.status = status || null;
    this.topic = topic || null;
  }
}

export default Game;

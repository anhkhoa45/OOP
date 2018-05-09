class Game {
  constructor({id, master, guest, mode, status}) {
    this.id = id;
    this.master = master || null;
    this.guest = guest || null;
    this.mode = mode || null;
    this.status = status || null;
  }
}

export default Game;

class Player {
  constructor(id, name, avatar){
    this.id = id;
    this.name = name;
    this.avatar = avatar;
    this.answers = [];
  }

  updateState({answers}){
    this.answers = answers;
  }
}

export default Player;

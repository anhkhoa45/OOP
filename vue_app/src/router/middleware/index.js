import {handler as redirectIfAuthenticated} from './redirectIfAuthenticated'
import {handler as requireLogin} from './requireLogin'
import {handler as joinedGame} from './joinedGame'
import {handler as socketConnected} from './socketConnected'

export default {
  redirectIfAuthenticated, requireLogin, joinedGame, socketConnected
}

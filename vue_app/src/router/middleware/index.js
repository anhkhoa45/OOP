import {handler as redirectIfAuthenticated} from './redirectIfAuthenticated'
import {handler as requireLogin, handler2} from './requireLogin'
import {handler as joinedGame} from './joinedGame'

export default {
  redirectIfAuthenticated, requireLogin, joinedGame
}

import ConfigurationService from "./configuration-service";
import RequestService from './request-service'
let instance = null;

export default class UserService {

  constructor() {

    if (instance) {
      return instance
    } else {
      instance = this
    }

    this.currentUser = null
    this.configurationService = ConfigurationService()

    superagent.set("Authorization", `Bearer ${this.configurationService.get('accessToken')}`)

    return instance;
  }

  login(username, password) {

  }

  get me() {
    return this.currentUser
  }

  validate(cb) {
    RequestService().get('/api/users/validate').then((resp)=>{
      debugger
    })

    // end((err, res)=> {
    //   let loggedIn = false
    //   if (!err) {
    //     loggedIn = true
    //   }
    //   this.setState(assign(this.state, {loginVerified: true, loggedIn: loggedIn}))
    // });
    //
    //
    // if (cb) {
    //
    // }
  }
}

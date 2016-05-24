import ConfigurationService from "./configuration-service";
import RequestService from "./request-service";
let instance = null;

export default class UserService {

  constructor() {

    if (instance) {
      return instance
    } else {
      instance = this
    }

    this.currentUser = null
    this.configurationService = new ConfigurationService()
    this.requestService = new RequestService()

    return instance;
  }

  logout() {
    this.requestService.setAccessToken("invalid")
    this.currentUser = null
    return this.requestService.post('/api/users/logout', {}).then((resp)=> {
      if (resp.status === 200) {
        return resp
      } else {
        var error = new Error(response.statusText)
        error.response = response
        throw error
      }
    })
  }

  login(username, password) {
    return new Promise((resolve, reject)=> {
      this.requestService.post('/api/users/login', {username: username, password: password}).then((resp)=> {
        if (resp.status === 200) {
          return resp.json()
        } else {
          var error = new Error(response.statusText)
          error.response = response
          reject(error)
        }
      }).then((json)=> {
        this.requestService.setAccessToken(json.access_token)
        this.setMe()
        resolve(json)
      })
    })
  }

  get me() {
    return new Promise((resolve, reject)=> {
      if(this.currentUser) {
        resolve(this.currentUser)
      } else {

      }
    })
  }

  setMe() {
    this.requestService.get('/api/users/me').then((resp)=> {
      if(resp.status === 200) {
        return resp.json()
      } else {
        var error = new Error(response.statusText)
        error.response = response
        reject(error)
      }
    }).then((json)=> {
      this.currentUser = json.user
    })
  }

  validate() {
    return this.requestService.get('/api/users/validate').then((resp)=> {
      if (resp.status === 200) {
        this.setMe()
        return resp.json()
      } else {
        var error = new Error(response.statusText)
        error.response = response
        throw error
      }
    })
  }
}

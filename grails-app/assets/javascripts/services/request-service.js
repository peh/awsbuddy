import ConfigurationService from "./configuration-service";
import {assign} from "lodash";
import * as fetch from "whatwg-fetch";

let instance = null;

export default class RequestService {

  constructor() {
    if (instance) {
      return instance
    } else {
      instance = this
    }

    this.configurationService = ConfigurationService()

    this.defaultHeaders = {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }

    let accessToken = this.configurationService.get('accessToken')
    if (accessToken) {
      this.setAccessToken(accessToken)
    }


    return instance;
  }

  setAccessToken(accessToken) {
    this.defaultHeaders = assign(this.defaultHeaders, {"Authorization": `Bearer ${accessToken}`})
  }

  get(url) {
    return fetch(url, {
      method: 'GET',
      headers: this.defaultHeaders
    })
  }

  post(url, data) {
    /***
       fetch('/users', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    name: 'Hubot',
    login: 'hubot',
  })
})
     */
  }
}

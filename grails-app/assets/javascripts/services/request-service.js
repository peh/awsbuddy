import ConfigurationService from "./configuration-service";
import {assign} from "lodash";
import "whatwg-fetch";

let instance = null;

export default class RequestService {

  constructor() {
    if (instance) {
      return instance
    } else {
      instance = this
    }

    this.configurationService = new ConfigurationService()

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
    this.configurationService.set('accessToken', accessToken)
    this.defaultHeaders = assign(this.defaultHeaders, {"Authorization": `Bearer ${accessToken}`})
  }

  get(url) {
    return fetch(url, {
      method: 'GET',
      headers: this.defaultHeaders
    })
  }

  post(url, data) {
    return fetch(url, {
      method: 'POST',
      headers: this.defaultHeaders,
      body: JSON.stringify(data)
    })
  }
}

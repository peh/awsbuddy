const CONFIG_KEY = "awsbuddy.config"

let instance = null;

export default class ConfigurationService {

  constructor() {
    if (instance) {
      return instance
    } else {
      instance = this
    }

    this.config = {}
    let fromStore = localStorage.getItem(CONFIG_KEY)
    if (fromStore === null) {
      this.config = {}
      this.flush()
    } else {
      this.config = JSON.parse(fromStore)
    }

    return instance;
  }

  flush() {
    localStorage.setItem(CONFIG_KEY, JSON.stringify(this.config))
  }

  get(key) {
    return this.config[key]
  }

  set(key, value) {
    this.config[key] = value
    this.flush()
  }
}

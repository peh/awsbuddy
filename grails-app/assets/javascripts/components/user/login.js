import React from "react";
import {assign} from "lodash";
import ConfigurationService from "../../services/configuration-service";
export default class Login extends React.Component {

  constructor(props) {
    super(props)
    this.state = {
      username: "",
      password: ""
    }
    this.onInputChange = this.onInputChange.bind(this)
    this.onFormSubmit = this.onFormSubmit.bind(this)
  }

  onInputChange(e) {
    let newState = assign(this.state)
    newState[e.target.id] = e.target.value
    this.setState(newState)
  }

  onFormSubmit() {
    let {username, password} = this.state
    superagent.post("/api/users/login").send({username: username, password: password}).end((err, res) => {
      if(!err) {
        ConfigurationService.set('accessToken', res.body.access_token)
        superagent.set("Authorization", `Bearer ${res.body.access_token}`)
      }
    })
  }

  render() {
    return (
      <form autocomplete="off" onSubmit={(e)=>{
      e.preventDefault()
      this.onFormSubmit()
      }}>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input type="text" className="form-control" id="username" value={this.state.username} onChange={this.onInputChange} placeholder="Username"/>
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input type="password" className="form-control" id="password" value={this.state.password} onChange={this.onInputChange} placeholder="Password"/>
        </div>
        <button type="submit" className="btn btn-default">Submit</button>
      </form>
    )
  }
}

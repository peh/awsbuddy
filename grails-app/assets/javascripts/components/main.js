import React from "react";
import {assign} from "lodash";
import UserService from "../services/user-service";
import Navbar from "./navbar/navbar";
import Login from "./user/login";
const RouterMixin = require('react-mini-router').RouterMixin;
const navigate = require('react-mini-router').navigate;

var Main = React.createClass({
  mixins: [RouterMixin],

  routes: {
    '/': 'home',
    '/login': 'login'
  },

  getInitialState() {
    return {
      loginVerified: false,
      loggedIn: false
    }
  },

  componentWillMount() {
    new UserService().validate().then((json) => {
      this.onLoginSuccess()
    }).catch((error)=> {
      this.onLogout()
    })
  },

  home: function () {
    return <div> Hallo!!!</div>
  },

  onLoginSuccess: function () {
    this.setState(assign(this.state, {loginVerified: true, loggedIn: true}))
    navigate('/', false)
  },

  onLogout: function () {
    new UserService().logout()
    this.setState(assign(this.state, {loginVerified: true, loggedIn: false}))
  },

  render: function () {
    if (!this.state.loginVerified) {
      return <div>Loading</div>
    } else {
      let renderComponent = null
      if (!this.state.loggedIn) {
        navigate('/login', false)
        renderComponent = <Login onLogin={this.onLoginSuccess}/>
      } else {
        renderComponent = this.renderCurrentRoute()
      }
      return (
        <div>
          <Navbar loggedIn={false} onLogout={this.onLogout}/>
          <div className="container">
            {renderComponent}
          </div>
        </div>
      )
    }
  },

  notFound: function (path) {
    return <div class="not-found">Page Not Found: {path}</div>;
  }

});

module.exports = Main;

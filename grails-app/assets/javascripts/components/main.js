import React from "react";
import Navbar from "./navbar/navbar";
import Login from "./user/login"
import {assign} from "lodash";
const RouterMixin = require('react-mini-router').RouterMixin;
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
    superagent.get('/api/users/validate').end((err, res)=> {
      let loggedIn = false
      if (!err) {
        loggedIn = true
      }
      this.setState(assign(this.state, {loginVerified: true, loggedIn: loggedIn}))
    });
  },

  home: ()=> {
    return <div> Hallo!!!</div>
  },

  render: function () {
    if (!this.state.loginVerified) {
      return <div>Loading</div>
    } else {
      let toRender = null
      if (!this.state.loggedIn) {
        toRender = <Login />
      } else {
        toRender = this.renderCurrentRoute()
      }
      return (
        <div>
          <Navbar loggedIn={false}/>
          <div className="container">
            {toRender}
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

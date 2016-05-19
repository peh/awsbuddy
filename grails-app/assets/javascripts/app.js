import ReactDOM from "react-dom";
import React from "react";
import ConfigurationService from "./services/configuration-service";
import RequestService from "./services/request-service";
const Main = require('./components/main.js');


// initialize the two most important singletons before everything else!
RequestService()
ConfigurationService()


ReactDOM.render(<Main />, document.getElementById('app-container'));

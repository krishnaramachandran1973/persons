import React from "react";
import ReactDOM from "react-dom";
import Login from "./Login";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter } from "react-router-dom";
import RoutesSetup from "./RoutesSetup";
import NavBar from "./NavBar";
import store from "./store";
import { Provider } from "react-redux";

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <NavBar />
      <RoutesSetup />
    </BrowserRouter>
  </Provider>,
  document.getElementById("root")
);

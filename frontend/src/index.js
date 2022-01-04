import React from "react";
import ReactDOM from "react-dom";
import Login from "./Login";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter } from "react-router-dom";
import RoutesSetup from "./RoutesSetup";
import NavBar from "./NavBar";

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <NavBar />
      <RoutesSetup />
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);

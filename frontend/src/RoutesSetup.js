import { Route, Routes } from "react-router-dom";
import Login from "./Login";
import People from "./People";

const RoutesSetup = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="people" element={<People />} />
    </Routes>
  );
};

export default RoutesSetup;

import axios from "axios";
import { useEffect, useState } from "react";
import { URL } from "./Constants";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    username: "",
    password: "",
  });

  useEffect(() => {
    if (localStorage.getItem("jwt") !== null) {
      navigate("/people", { replace: true });
    }
  }, []);

  const handleLogin = (e) => {
    e.preventDefault();
    axios
      .post(`${URL}/authenticate`, {
        username: user.username,
        password: user.password,
      })
      .then((response) => {
        console.log(response.data);
        localStorage.setItem("jwt", response.data.jwt);
        navigate("/people", { replace: true });
      });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  return (
    <>
      <div className="container border rounded m-5 p-5">
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label htmlFor="username" className="form-label">
              Username
            </label>
            <input
              type="text"
              className="form-control"
              id="username"
              name="username"
              value={user.username}
              onChange={handleChange}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              id="password"
              name="password"
              value={user.password}
              onChange={handleChange}
            />
          </div>

          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>
    </>
  );
};

export default Login;

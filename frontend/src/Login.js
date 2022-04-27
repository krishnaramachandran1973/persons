import axios from "axios";
import { useEffect, useState } from "react";
import { URL } from "./Constants";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { userLoggedin } from "./features/authentication/loginSlice";

const errors = {};

const Login = () => {
  const { isLoggedIn } = useSelector((state) => state.login);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [user, setUser] = useState({
    username: "",
    password: "",
  });
  const [formErrors, setFormErrors] = useState({});

  useEffect(() => {
    if (isLoggedIn) {
      navigate("/people", { replace: true });
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
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
        dispatch(userLoggedin());
        navigate("/people", { replace: true });
      });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "username") {
      if (value === "" || value.length < 5) {
        errors.username = "Username is required";
      } else {
        delete errors.username;
      }
    }
    if (name === "password") {
      if (value === "" || value.length < 5) {
        errors.password = "Password is required";
      } else {
        delete errors.password;
      }
    }

    if (Object.keys(errors).length !== 0) {
      setFormErrors(errors);
    } else {
      setFormErrors({});
    }
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
            {formErrors && (
              <span className="text-danger">{formErrors.username}</span>
            )}
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
            {formErrors && (
              <span className="text-danger">{formErrors.password}</span>
            )}
          </div>

          <button
            type="submit"
            className="btn btn-primary"
            disabled={Object.keys(formErrors).length !== 0}
          >
            Submit
          </button>
        </form>
      </div>
    </>
  );
};

export default Login;

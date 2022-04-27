import { Link, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { userLoggedOut } from "./features/authentication/loginSlice";

const NavBar = () => {
  const { isLoggedIn } = useSelector((state) => state.login);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <Link className="navbar-brand" to="/">
          Home
        </Link>

        <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
          <div className="navbar-nav">
            <Link className="nav-link" to="/people">
              People
            </Link>
            {isLoggedIn && (
              <Link
                to=""
                className="nav-link"
                onClick={() => {
                  localStorage.removeItem("jwt");
                  dispatch(userLoggedOut());
                  navigate("/", { replace: true });
                }}
              >
                Logout
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;

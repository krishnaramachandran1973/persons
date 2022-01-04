import { Link, useNavigate } from "react-router-dom";

const NavBar = () => {
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
            <a
              className="nav-link"
              href="#"
              onClick={() => {
                localStorage.removeItem("jwt");
                navigate("/", { replace: true });
              }}
            >
              Logout
            </a>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;

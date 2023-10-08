import "./Sidebar.css";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";


export default function Sidebar() {

  const navigate = useNavigate();
  const { setIsAuthenticated, setToken } = useAuth();

  /*
    Update the state variables to no auth and no token.
    Then direct the user back to the login page.
  */
  const handleLogout = async () => {
    setIsAuthenticated(false);
    setToken(null);
    navigate("/login");
  };

  /*
    Original route method: 
      const navigate = useNavigate();
      onClick={() => navigate("/page")}
    Changed to <Link> due to Phase 3 requirement.
  */
  return (
    <nav className="sidebar">
      <ul className="sidebar-list">
        <li className="sidebar-item">
          <Link to="/dashboard" className="disable-link">Dashboard</Link>
        </li>
        <li className="sidebar-item" >
          <Link to="/vault/locked" className="disable-link">Vault</Link>
        </li>
        <li className="sidebar-item">
          <Link to="/password-strength-checker" className="disable-link">
            Password Strength Checker
            </Link> 
        </li>
        <li className="sidebar-item">
          <Link to="/password-strength-checker" className="disable-link" onClick={handleLogout} >
            Logout
            </Link> 
        </li>
      </ul>
    </nav>
  );
}
import { Route, Routes, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "./auth/AuthContext";
import { useState, useEffect } from "react";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Vault from "./pages/Vault"; // Import the Vault component
import PasswordStrengthChecker from "./pages/PasswordStrengthChecker";
import LockedVault from "./pages/LockedVault";
import "./App.css";

/* 
> A component that secures access to other components based on whether if a user is authenticated or not.
> Prop: component, destructured to "Component" for later reuse.
> The hook useAuth() returns an object with an "isAuthenticated" property: boolean for user authentication.
> If the user is not authenticated, go back to <Login />, otherwise, renders the desired component/page that was passed in as a prop.
*/
const ProtectedElement = ({ component: Component, ...rest }) => {
  const { isAuthenticated } = useAuth();
  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }
  return <Component {...rest}/>;
};

export default function App() {

  const [me, setMe] = useState("");
  useEffect(() => {
    if (localStorage.getItem("me") === undefined) {
      return;
    } else {
      setMe(localStorage.getItem("me"));
    }
  }, []);

  return (
    <div className="App">
      {/* Using Context API for authentication and Routes to switch URLs */}
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Navigate to="/login" />} />
          <Route path="/login" element={<Login />} />
          {/* All the ProtectedElement needs the initial user authentication */}
          <Route
            path="/dashboard"
            element={<ProtectedElement component={Dashboard} />}
          />
          {/* Sign in again to access vault */}
          <Route
            path="/vault/locked"
            element={<ProtectedElement component={LockedVault} me={setMe}/>}
          />
          <Route
            path="/vault/access"
            element={<ProtectedElement component={Vault} me={me}/>}
          />
          <Route
            path="/password-strength-checker"
            element={<ProtectedElement component={PasswordStrengthChecker} />}
          />
        </Routes>
      </AuthProvider>
    </div>
  );
}
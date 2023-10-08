/* 
> Using the React Context API instead of localstorage for initial login for a bit more security.
*/

import React, { createContext, useContext, useState, useEffect } from "react";
import jwtDecode from "jwt-decode";

// Create a new context (global state)
const AuthContext = createContext();

// Create useAuth to allow components to access AuthContext
export const useAuth = () => useContext(AuthContext);

// AuthProvider = wrapper component wrapping all child components with AuthContext.Provider (data is now available to all child components)
export const AuthProvider = ({ children }) => {
  // Initial authentication state === false.
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  // Initial JWT token state === null.
  const [token, setToken] = useState(null);
  // No email set
  const [currentUserEmail, setCurrentUserEmail] = useState('');

  // Runs whenever the token changes
  useEffect(() => {
    if (token) {
      const decodedToken = jwtDecode(token);
      setCurrentUserEmail(decodedToken.sub);
    }
  }, [token]);

  // the return block renders the AuthContext.Provider, setting its value prop to the state variables and methods that manage them. 
  // The children are rendered inside this provider, which means they can now access this AuthContext.
  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        token,
        setToken,
        currentUserEmail,
        setCurrentUserEmail
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
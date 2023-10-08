import { Route, Navigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';

export default ({ element, ...rest }) => {
  const { isAuthenticated } = useAuth();

  return (
    <Route {...rest} element={
      isAuthenticated ? element : <Navigate to="/login" />
    } />
  );
};
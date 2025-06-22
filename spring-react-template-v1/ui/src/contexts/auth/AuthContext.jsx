import { createContext, useEffect, useReducer } from 'react';
import authReducer from './AuthReducer';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const initialState = (() => {
    try {
      const jwtToken = localStorage.getItem('jwtToken');
      if (jwtToken) {
        return {
          jwtToken,
          isAuthenticated: true,
        };
      }
    } catch (error) {
      console.error('Failed to load from localStorage:', error);
    }
    return {
      jwtToken: null,
      isAuthenticated: false,
    };
  })();

  const [state, dispatch] = useReducer(authReducer, initialState);

  useEffect(() => {
    try {
      if (state.isAuthenticated) {
        localStorage.setItem('jwtToken', state.jwtToken);
      } else {
        localStorage.removeItem('jwtToken');
      }
    } catch (error) {
      console.error('Failed to save to localStorage:', error);
    }
  }, [state]);

  return (
    <AuthContext.Provider
      value={{
        ...state,
        dispatch,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;

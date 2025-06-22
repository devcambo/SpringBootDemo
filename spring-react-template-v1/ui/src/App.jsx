import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import MainLayout from './components/MainLayout';
import Homepage from './pages/Homepage';
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';
import ForgotPwdForm from './components/ForgotPwdForm';
import ResetPwdForm from './components/ResetPwdForm';
import ProfilePage from './pages/ProfilePage';
import NotFoundPage from './pages/NotFoundPage';
import { AuthProvider } from './contexts/auth/AuthContext';

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path='/' element={<MainLayout />}>
            <Route index element={<Homepage />} />
            <Route path='/register' element={<RegisterForm />} />
            <Route path='/login' element={<LoginForm />} />
            <Route path='/forgot-password' element={<ForgotPwdForm />} />
            <Route path='/reset-password' element={<ResetPwdForm />} />
            <Route path='/profile' element={<ProfilePage />} />
            <Route path='*' element={<NotFoundPage />} />
          </Route>
        </Routes>
      </Router>
      <ToastContainer />
    </AuthProvider>
  );
};

export default App;

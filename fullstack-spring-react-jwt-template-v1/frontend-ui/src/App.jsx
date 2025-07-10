import React from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import HomePage from './pages/HomePage'
import RegisterPage from './pages/RegisterPage'
import LoginPage from './pages/LoginPage'
import ForgotPwdPage from './pages/ForgotPwdPage'
import ResetPwdPage from './pages/ResetPwdPage'
import ProfilePage from './pages/ProfilePage'
import AboutPage from './pages/AboutPage'
import NotFoundPage from './pages/NotFoundPage'
import MainNavbar from './components/MainNavbar'
import MainFooter from './components/MainFooter'
import { AuthProvider } from './context/auth/AuthContext'
import ProtectedRoute from './components/ProtectedRoute'
import { UserProvider } from './context/user/UserContext'

const App = () => {
  return (
    <AuthProvider>
      <UserProvider>
        <Router>
          <MainNavbar />
          <Routes>
            <Route path='/' element={<HomePage />} />
            <Route path='/register' element={<RegisterPage />} />
            <Route path='/login' element={<LoginPage />} />
            <Route path='/forgot-password' element={<ForgotPwdPage />} />
            <Route path='/reset-password' element={<ResetPwdPage />} />
            <Route path='/about' element={<AboutPage />} />
            <Route element={<ProtectedRoute />}>
              <Route path='/profile' element={<ProfilePage />} />
            </Route>
            <Route path='*' element={<NotFoundPage />} />
          </Routes>
          <MainFooter />
        </Router>
      </UserProvider>
    </AuthProvider>
  )
}

export default App

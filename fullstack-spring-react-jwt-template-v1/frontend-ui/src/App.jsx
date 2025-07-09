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

const App = () => {
  return (
    <Router>
      <MainNavbar />
      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/register' element={<RegisterPage />} />
        <Route path='/login' element={<LoginPage />} />
        <Route path='/forgotpwd' element={<ForgotPwdPage />} />
        <Route path='/resetpwd' element={<ResetPwdPage />} />
        <Route path='/profile' element={<ProfilePage />} />
        <Route path='/about' element={<AboutPage />} />
        <Route path='*' element={<NotFoundPage />} />
      </Routes>
      <MainFooter />
    </Router>
  )
}

export default App

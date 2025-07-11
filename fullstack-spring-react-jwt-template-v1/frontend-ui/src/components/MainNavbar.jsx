import React, { useContext } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthContext from '../context/auth/AuthContext'

const MainNavbar = () => {
  const { isAuthenticated, dispatch } = useContext(AuthContext)
  const navigate = useNavigate()

  const handleLogout = (e) => {
    e.preventDefault()
    dispatch({ type: 'LOGOUT' })
    setTimeout(() => navigate('/'), 0) // temporary solution
  }

  return (
    <div>
      MainNavbar <br />
      <Link to='/'>Home</Link>
      <br />
      <Link to='/register'>Register</Link>
      <br />
      <Link to='/login'>Login</Link>
      <br />
      <Link to='/forgot-password'>Forgot Password</Link>
      <br />
      <Link to='/reset-password'>Reset Password</Link>
      <br />
      <Link to='/about'>About</Link>
      <br />
      {isAuthenticated && (
        <div>
          <Link to='/profile'>Profile</Link>
          <br />
          <button onClick={handleLogout}>Logout</button>
        </div>
      )}
      <hr />
    </div>
  )
}

export default MainNavbar

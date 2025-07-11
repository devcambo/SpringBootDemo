import React, { useContext } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import AuthContext from '../context/auth/AuthContext'

const MainNavbar = () => {
  const { dispatch } = useContext(AuthContext)
  const navigate = useNavigate()

  const handleLogout = (e) => {
    e.preventDefault()
    console.log('Dispatching LOGOUT')
    dispatch({ type: 'LOGOUT' })
    console.log('Navigating to /')
    setTimeout(() => navigate('/'), 10) // temporary solution
    console.log('Navigated to /')
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
      <Link to='/profile'>Profile</Link>
      <br />
      <Link to='/about'>About</Link>
      <br />
      <button onClick={handleLogout}>Logout</button>
      <hr />
    </div>
  )
}

export default MainNavbar

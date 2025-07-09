import React from 'react'
import { Link } from 'react-router-dom'

const MainNavbar = () => {
  return (
    <div>
      MainNavbar <br />
      <Link to='/'>Home</Link>
      <br />
      <Link to='/register'>Register</Link>
      <br />
      <Link to='/login'>Login</Link>
      <br />
      <Link to='/forgotpwd'>Forgot Password</Link>
      <br />
      <Link to='/resetpwd'>Reset Password</Link>
      <br />
      <Link to='/profile'>Profile</Link>
      <br />
      <Link to='/about'>About</Link>
      <br />
      <hr />
    </div>
  )
}

export default MainNavbar

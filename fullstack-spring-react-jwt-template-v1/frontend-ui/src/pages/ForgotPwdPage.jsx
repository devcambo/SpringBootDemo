import React, { useState } from 'react'
import { forgotPassword } from '../context/auth/AuthActions'
import { useNavigate } from 'react-router-dom'

const ForgotPwdPage = () => {
  const [email, setEmail] = useState('')

  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    const userEmail = {
      email,
    }
    try {
      await forgotPassword(userEmail)
      setEmail('')
      navigate('/login')
    } catch (error) {
      console.log(error)
      return
    }
  }

  return (
    <div>
      ForgotPwdPage
      <form onSubmit={handleSubmit}>
        <label htmlFor='email'>Email</label>
        <input
          type='email'
          name='email'
          id='email'
          onChange={(e) => setEmail(e.target.value)}
          value={email}
        />{' '}
        <br />
        <input type='submit' value='Send link to email' />
      </form>
    </div>
  )
}

export default ForgotPwdPage

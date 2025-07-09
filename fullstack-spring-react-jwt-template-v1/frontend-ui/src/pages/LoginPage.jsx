import React, { useContext, useState } from 'react'
import AuthContext from '../context/auth/AuthContext'
import { loginUser } from '../context/auth/AuthActions'
import { useNavigate } from 'react-router-dom'

const LoginPage = () => {
  const [inputs, setinputs] = useState({})

  const { dispatch } = useContext(AuthContext)
  const navigate = useNavigate()

  const handleChange = (e) => {
    const name = e.target.name
    const value = e.target.value
    setinputs((values) => ({ ...values, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    const user = {
      email: inputs.email,
      password: inputs.password,
    }
    try {
      const res = await loginUser(user)
      dispatch({ type: 'LOGIN', payload: res.access_token })
      clearForm()
      navigate('/profile')
    } catch (error) {
      console.log(error)
      return
    }
  }

  const clearForm = () => {
    setinputs({})
  }

  return (
    <div>
      LoginPage
      <form onSubmit={handleSubmit}>
        <label htmlFor='email'>Email</label>
        <input
          type='email'
          name='email'
          id='email'
          onChange={handleChange}
        />{' '}
        <br />
        <label htmlFor='password'>Password</label>
        <input
          type='password'
          name='password'
          id='password'
          onChange={handleChange}
        />{' '}
        <br />
        <input type='submit' value='Login' />
      </form>
    </div>
  )
}

export default LoginPage

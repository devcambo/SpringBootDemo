import React, { useState } from 'react'

const LoginPage = () => {
  const [inputs, setinputs] = useState({})

  const handleChange = (e) => {
    const name = e.target.name
    const value = e.target.value
    setinputs((values) => ({ ...values, [name]: value }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log(inputs)
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

import React, { useState } from 'react'

const ForgotPwdPage = () => {
  const [email, setEmail] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log(email)
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

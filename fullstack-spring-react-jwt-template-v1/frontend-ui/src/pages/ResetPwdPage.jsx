import React, { useState } from 'react'

const ResetPwdPage = () => {
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const handleSubmit = (e) => {
    e.preventDefault()
    if (newPassword !== confirmPassword) {
      alert('Passwords do not match')
      return
    }
    console.log(newPassword, confirmPassword)
  }

  return (
    <div>
      ResetPwdPage
      <form onSubmit={handleSubmit}>
        <label htmlFor='newPassword'>New Password</label>
        <input
          type='password'
          name='newPassword'
          id='newPassword'
          onChange={(e) => setNewPassword(e.target.value)}
          value={newPassword}
        />{' '}
        <br />
        <label htmlFor='confirmPassword'>Confirm Password</label>
        <input
          type='password'
          name='confirmPassword'
          id='confirmPassword'
          onChange={(e) => setConfirmPassword(e.target.value)}
          value={confirmPassword}
        />{' '}
        <br />
        <input type='submit' value='Reset password' />
      </form>
    </div>
  )
}

export default ResetPwdPage

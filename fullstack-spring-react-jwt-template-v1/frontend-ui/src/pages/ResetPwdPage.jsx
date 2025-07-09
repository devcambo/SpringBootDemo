import React, { useState } from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { resetPassword } from '../context/auth/AuthActions'

const ResetPwdPage = () => {
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const [searchParams] = useSearchParams()
  const token = searchParams.get('token')

  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (newPassword !== confirmPassword) {
      alert('Passwords do not match')
      return
    }
    const userPassword = {
      newPassword,
      confirmPassword,
    }
    try {
      await resetPassword(token, userPassword)
      clearForm()
      navigate('/login')
    } catch (error) {
      console.log(error)
      return
    }
  }

  const clearForm = () => {
    setNewPassword('')
    setConfirmPassword('')
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

import React, { useEffect, useState } from 'react'
import { findUserProfile, updateUser } from '../context/user/UserActions'

const ProfilePage = () => {
  const [profile, setProfile] = useState({})

  const fetchUserProfile = async () => {
    try {
      const res = await findUserProfile()
      setProfile(res)
    } catch (error) {
      console.log(error)
      return
    }
  }

  useEffect(() => {
    fetchUserProfile()
  }, [])

  const handleSubmit = (e) => {
    e.preventDefault()
    const user = {
      username: profile.username,
      profilePicture: profile.profilePicture,
    }
    try {
      updateUser(profile.id, user)
      setProfile({})
    } catch (error) {
      console.log(error)
      return
    }
  }

  return (
    <div>
      <h4>User Information</h4>
      <form onSubmit={handleSubmit}>
        <label htmlFor='username'>Username</label>
        <input
          type='text'
          name='username'
          id='username'
          value={profile.username || ''}
          onChange={(e) => setProfile({ ...profile, username: e.target.value })}
        />{' '}
        <br />
        <label htmlFor='email'>Email</label>
        <input
          type='email'
          name='email'
          id='email'
          disabled
          value={profile.email || ''}
          onChange={(e) => setProfile({ ...profile, email: e.target.value })}
        />{' '}
        <br />
        <label htmlFor='profilePicture'>Profile Picture</label>
        <img src={profile.profilePicture || 'abc.png'} alt='' /> <br />
        <label htmlFor='roles'>Roles</label>
        <input
          type='text'
          name='roles'
          id='roles'
          disabled
          value={profile.roles || ''}
          onChange={(e) => setProfile({ ...profile, roles: e.target.value })}
        />{' '}
        <br />
        <label htmlFor='createdAt'>Created At</label>
        <input
          type='text'
          name='createdAt'
          id='createdAt'
          disabled
          value={profile.createdAt || ''}
          onChange={(e) =>
            setProfile({ ...profile, createdAt: e.target.value })
          }
        />{' '}
        <br />
        <label htmlFor='updatedAt'>Updated At</label>
        <input
          type='text'
          name='updatedAt'
          id='updatedAt'
          disabled
          value={profile.updatedAt || ''}
          onChange={(e) =>
            setProfile({ ...profile, updatedAt: e.target.value })
          }
        />
        <input type='submit' value='Update Profile' />
      </form>
    </div>
  )
}

export default ProfilePage

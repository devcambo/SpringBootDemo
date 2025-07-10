import React, { useEffect, useState } from 'react'
import { findUserProfile } from '../context/user/UserActions'

const ProfilePage = () => {
  const [profile, setProfile] = useState({})

  const fetchUserProfile = async () => {
    try {
      const res = await findUserProfile()
      setProfile(res)
    } catch (error) {}
  }

  useEffect(() => {
    fetchUserProfile()
  }, [])

  return (
    <div>
      <h4>User Information</h4>
      <p>Username: {profile.username}</p>
      <p>Email: {profile.email}</p>
      <img src={profile.profilePicture} alt='profile' />
      <p>Roles: {profile.roles}</p>
      <p>Created At: {profile.createdAt}</p>
      <p>Updated At: {profile.updatedAt}</p>
    </div>
  )
}

export default ProfilePage

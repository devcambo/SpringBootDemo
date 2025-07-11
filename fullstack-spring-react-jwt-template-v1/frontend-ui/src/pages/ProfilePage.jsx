import React, { useEffect, useState } from 'react'
import { findUserProfile, updateUser } from '../context/user/UserActions'
import { uploadFile } from '../context/file/FileActions'

const ProfilePage = () => {
  const [profile, setProfile] = useState({})
  const [selectedFile, setSelectedFile] = useState(null)

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

  const handleSubmit = async (e) => {
    e.preventDefault()
    const uploaded = await handleUploadFile()
    const fileName = uploaded.fileName
    console.log(fileName)
    const user = {
      username: profile.username,
      profilePicture: fileName,
    }
    try {
      updateUser(profile.id, user)
      setProfile({})
    } catch (error) {
      console.log(error)
      return
    }
  }

  const handleUploadFile = async () => {
    const data = new FormData()
    data.append(
			"file",
			selectedFile,
			selectedFile.name
		);
    console.log(data)
    try {
      await uploadFile(data)
    } catch (error) {
      console.log(error)
      return
    }
  }

  return (
    <div>
      <h4>User Information</h4>
      <form onSubmit={handleSubmit}>
        {/* Username */}
        <label htmlFor='username'>Username</label>
        <input
          type='text'
          name='username'
          id='username'
          value={profile.username || ''}
          onChange={(e) => setProfile({ ...profile, username: e.target.value })}
        />{' '}
        <br />
        {/* Email */}
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
        {/* Profile Picture */}
        <label htmlFor='profilePicture'>Profile Picture</label>
        <img src={profile.profilePicture || 'abc.png'} alt='' /> <br />
        <input
          type='file'
          name='profilePicture'
          id='profilePicture'
          onChange={(e) => setSelectedFile(e.target.files[0])}
        />
        {/* Roles */}
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
        {/* Created At */}
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
        {/* Updated At */}
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
        {/* Submit */}
        <input type='submit' value='Update Profile' />
      </form>
    </div>
  )
}

export default ProfilePage

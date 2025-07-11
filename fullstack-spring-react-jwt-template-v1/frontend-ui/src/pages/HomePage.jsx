import React, { useEffect } from 'react'

const HomePage = () => {
  useEffect(() => {
  console.log('HomePage rendered')
}, [])
  return <div>HomePage</div>
}

export default HomePage

import React from 'react'
import UpdateMember from './updateMember'
import { useParams } from 'react-router-dom'

function MemberUpdate() {
    const {groupId , username} = useParams() ;
  return (
    <UpdateMember groupId={groupId} username={username}/>
  )
}

export default MemberUpdate

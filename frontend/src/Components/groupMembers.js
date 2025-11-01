import React from 'react'
import AddMember from './addMember'
import { useParams } from 'react-router-dom'

function GroupMembers() {

    const {groupId} = useParams() ;
  return (
    <AddMember groupId = {groupId}/>
  )
}

export default GroupMembers

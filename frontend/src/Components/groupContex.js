import React, { createContext, useState, useContext } from "react";
import GlobalLoader from "./loader";


const GroupContext = createContext() ;

function GroupProvider() {

    const [groups , setGroups] = useState(new Map()) ;

  const addGroup = (groupId, dto) => {
    const newGroups = new Map(groups);
    newGroups.set(groupId, dto);
    setGroups(newGroups);
  };

  return (
    <GroupContext.Provider value={{ groups, addGroup }}>
      <GlobalLoader/>
    </GroupContext.Provider>
  );
}

export default GroupProvider


export function useGroupContext() {
  return useContext(GroupContext);
}

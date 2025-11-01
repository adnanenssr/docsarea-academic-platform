import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import PaperList from './paper';
import FileViewPapers from './fileViewPapers';
import PapersWithDelete from './papersWithDelete';

function Published({groupId}) {

    const [files , setFiles] = useState([]) ;
    const [pages , setPages] = useState(1) ;
    const [elements , setElements] = useState(0) ;
    const [page , setPage] = useState(0) ;

    useEffect(() =>{
        const getPapers = async () => {
            const response = await fetch(`http://localhost:8082/${groupId}/file/published?page=${page}` , {
                method : "GET" ,
                credentials :"include"
            })
            if(!response.ok){
                throw new Error("could not fetch papers Please try again later") ;
            }
            const data = await response.json() ;
            if(data){
                setFiles(data.files) ;
                setPages(data.numPages) ;
                setElements(data.numElements) ;
            }

        }
        getPapers() ;
    } , [page, groupId])
  return (
    <PapersWithDelete files = {files}/>
  )
}

export default Published

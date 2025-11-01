import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import PaperList from './paper';
import FileReviewPapers from './fileReviewPapers';

function ReviewsInReview({groupId}) {
    const [files , setFiles] = useState([]) ;
    const [pages , setPages] = useState(1) ;
    const [elements , setElements] = useState(0) ;
    const [page , setPage] = useState(0) ;

    useEffect(() =>{
        const getPapers = async () => {
            const response = await fetch(`http://localhost:8082/${groupId}/review/review?page=${page}` , {
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
    <FileReviewPapers files = {files} groupId={groupId}/>
  )
}

export default ReviewsInReview

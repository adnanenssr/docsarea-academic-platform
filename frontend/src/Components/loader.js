// loaders.js
import { redirect } from 'react-router-dom';
import { groupCache } from './groupCache'; // ← Import the instance

export async function authenticationLoader({ request }) {
    const response = await fetch(`http://localhost:8082/authenticated`, {
        method: "GET",
        credentials: "include"
    });
    
    if (!response.ok) {
        if (response.status === 401) {
            const cached = groupCache.clear();
             const url = new URL(request.url);
             const redirectPath = url.pathname + url.search;
             return redirect(`/login?redirect=${encodeURIComponent(redirectPath)}`);
        }
        throw new Error("Authentication failed");
    }
    
    return null;
}

export async function loginAuthenticationLoader({ request , params }){
    const response = await fetch(`http://localhost:8082/authenticated`,{ 
        method : "GET" , 
        credentials : "include"
    });

    if(response.ok){
        

    }

}



export async function groupAccessLoader({ params , request }) {
    const { groupId } = params;
    
    const authResult = await authenticationLoader({request});
    if (authResult) return authResult;
    
    // Use the imported cache
    const cached = groupCache.get(groupId);
    
    
    const response = await fetch(`http://localhost:8082/get/member/${groupId}`, {
        method: "GET",
        credentials: "include"
    });
    
    if (!response.ok) {
        return redirect("/u/dashboard");
    }
    
    const data = await response.json();
    
    // Store in cache
    groupCache.set(groupId, data);
    
    return { 
        groupId, 
        groupData: data,
        fromCache: false 
    };
}

export async function reviewsLoader({ params , request }) {
    const { groupId } = params;
    
    const result = await groupAccessLoader({ params , request });
    
    if (result instanceof Response) {
        return result;
    }
    
    const { groupData } = result;
    
    if (!["OWNER","MODERATOR", "ADMIN"].includes(groupData.role)) {
        return redirect("/u/dashboard");
    }
    
    return result;
}

export async function filesLoader({ params , request }) {
    return await groupAccessLoader({ params , request });
}

export async function membersLoader({ params , request }) {
    return await groupAccessLoader({ params , request });
}

export async function settingsLoader({ params , request }){
    const result =  await groupAccessLoader({ params , request }) ;
    if(result instanceof Response){
        return result ;
    }

    const {groupData} = result ;
    if(groupData.role !== "OWNER"){
        return redirect("/u/dashboard");
    }
}


export async function insightsLoader({ params , request }) {
    return await groupAccessLoader({ params , request });
}

export async function dashboardLoader({ params , request }) {
    return await groupAccessLoader({ params , request });
}

export async function groupActivityLoader({params}){
    const result =  await groupAccessLoader({params}) ;
    if(result instanceof Response){
        return result ;
    }
    const {groupData} = result ;
    if(!["MEMBER" , "MODERATOR"].includes(groupData.role)){
        return redirect("/u/dashboard");
    }
}



export async function reviewPageAccessLoader({ params , request }){
    const result =  await groupAccessLoader({ params , request }) ;

    const { groupId } = params;
    const { fileId } = params;

    if(result instanceof Response){
        return result ;
    }

   
        const response = await fetch(`http://localhost:8082/review/get/${groupId}/${fileId}` , {
            method : "GET" ,
            credentials : "include",
            
        })
        if(!response.ok){
            throw new Error("could not get file review details")
        }
        
        const data = await response.json() ;

    

    const {groupData} = result ;
    if(( !["OWNER" , "ADMIN"].includes(groupData.role) && data.moderator !== groupData.username ) || data.status !== "IN_REVIEW" ){
        return redirect("/u/dashboard");
    }
    return { groupData, data };
}







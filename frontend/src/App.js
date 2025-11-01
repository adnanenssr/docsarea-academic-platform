import { AppBar, Box } from '@mui/material';
import { createBrowserRouter, RouterProvider, useLocation, Outlet } from 'react-router-dom';
import RegisterForm from "./Components/register";
import Upload from "./Components/upload";
import Login from './Components/login';
import FilePage from "./Components/FilePage";
import UpdateFile from "./Components/updateFile";
import UpdateGroup from "./Components/updateGroup";
import UpdateMember from "./Components/updateMember";
import MemberUpdate from "./Components/memberUpdate";
import CreateLink from "./Components/createLink";
import Link from "./Components/link";
import UserFiles from "./Components/userFiles";
import UserGroups from "./Components/userGroups";
import AddGroup from "./Components/addGroup";
import GroupAppBar from './Components/groupAppBar';
import GroupFilesPage from './Components/groupFilesPage';
import Members from './Components/members';
import GroupReviewsPage from './Components/groupReviewsPage';
import ViewsChart from './Components/viewsChart';
import Statistics from './Components/statistics';

import {
  reviewPageAccessLoader ,
  authenticationLoader,
  reviewsLoader,
  filesLoader,
  membersLoader,
  settingsLoader,
  insightsLoader,
  dashboardLoader,
  groupActivityLoader
} from './Components/loader';
import SideMenu from './Components/sideMenue';
import Appbar from './Components/appBar';
import GroupProfileDetails from './Components/groupProfileDetails';
import ReviewFile from './Components/reviewFile';
import Invitation from './Components/invitation';
import UserDashboard from './Components/dashboard';
import GroupDashboard from './Components/groupDashboard';
import Bookmarks from './Components/bookmarks';
import Downloads from './Components/downloads';
import Search from './Components/search';
import UserInsights from './Components/userInsights';
import UserProfilePage from './Components/userProfilePage';
import GroupProfilePage from './Components/groupProfilePage';
import UserProfile from './Components/userProfile';
import UserGroupsTabs from './Components/userGroupTabs';
import LandingPage from './Components/landingPage';
import PrivateResourcePage from './Components/privateResourcePage';
import AccountRequiredPage from './Components/authenticatedResourcePage';



function AppLayout() {
  const location = useLocation();
  
  // Pages with NO AppBar or SideMenu
  const publicPages = ['/login', '/register'];
  
  // Pages with GroupAppBar
  const groupPages = location.pathname.startsWith('/group/');
  
  const userPages = location.pathname.startsWith('/u/');

  const appBarPages = (!location.pathname.startsWith('/group/') && !location.pathname.startsWith('/p/') && (location.pathname.length > 2) && !location.pathname.startsWith('/login') && !location.pathname.startsWith('/register') );
  
  return (
    <Box sx={{ display: 'flex' }}>
      {/* Show GroupAppBar for group pages */}
      {groupPages && <GroupAppBar />}
      
      {/* Show Appbar + SideMenu for user pages */}
      {userPages && <SideMenu />}
      {appBarPages && <Appbar />}
      
      {/* Main content area */}
      <Box 
        component="main"
        sx={{ 
          flexGrow: 1,
          mt: (userPages || groupPages || appBarPages) ? '65px' : 0, // Offset for appbar height
          width: userPages ? 'calc(100% - 280px)' : '100%',
          
        }}
      >
        <Outlet /> {/* Child routes render here */}
      </Box>
    </Box>
  );
}

// Create the router with loaders
const router = createBrowserRouter([

  {

  path: "/",
    element: <AppLayout />, // Wrap everything with AppLayout
    children: [

  // Public routes
  {
    path: "/register",
    element: <RegisterForm />
  },
  {
    path: "/p/private/resource",
    element: <PrivateResourcePage />
  },
  {
    path: "/p/account/required",
    element: <AccountRequiredPage />
  },
  {
    path: "/login",
    element: <Login />

  },
  
  // Protected routes with authenticationLoader
  {
    path: "/u/dashboard",
    element: <UserDashboard />,
    loader: authenticationLoader
  },
  {
    path: "/u/bookmarks",
    element: <Bookmarks />,
    loader: authenticationLoader
  },
  {
    path: "/search",
    element: <Search />,
  },

  {
    path: "/u/downloads",
    element: <Downloads />,
    loader: authenticationLoader
  },
  {
    path: "",
    element: <LandingPage />,
  },
  {
    path: "/profile/group/:groupId",
    element: <GroupProfileDetails />,
    
  },
  {
    path: "/invitation/:id",
    element: <Invitation />,
    loader: authenticationLoader
    
  },
  {
    path: "/group/:groupId/settings",
    element: <GroupProfilePage />,
    loader: membersLoader
  },
  {
    path: "/u/insights",
    element: <UserInsights />,
    loader: authenticationLoader
    
  },
  {
    path: "/u/profile",
    element: <UserProfilePage />,
    loader: authenticationLoader
    
  },
  {
    path: "/profile/:username",
    element: <UserProfile />,
    
  },
  {
    path: "/:groupId/review/:fileId",
    element: <ReviewFile  />,
    loader: reviewPageAccessLoader

    
  },
  {
    path: "/group/:groupId",
    element: <GroupDashboard  />,
    loader: membersLoader

    
  },
 
  {
    path: "/u/files",
    element: <UserFiles />,
    loader: authenticationLoader
  },
  
  {
    path: "/u/updateFile/:fileId",
    element: <UpdateFile />,
    loader: authenticationLoader
  },
  {
    path: "/u/createlink",
    element: <CreateLink groupId="79e43768-adb7-45b1-b68b-1356dda0eb39" />,
    loader: authenticationLoader
  },
  {
    path: "/u/link/:linkId",
    element: <Link />,
    loader: authenticationLoader
  },
  {
    path: "/u/groups",
    element: <UserGroupsTabs />,
    loader: authenticationLoader
  },
  
  
  // Group routes with specific loaders
  {
    path: "/group/:groupId/files",
    element: <GroupFilesPage />,
    loader: filesLoader
  },
  {
    path: "/group/:groupId/members",
    element: <Members />,
    loader: membersLoader
  },
  {
    path: "/group/:groupId/reviews",
    element: <GroupReviewsPage />,
    loader: reviewsLoader
  },
  {
    path: "/group/:groupId/links",
    element: <Link />,
    loader: insightsLoader
  },
  {
    path: "/group/:groupId/insights",
    element: <Statistics />,
    loader: insightsLoader
  },
  {
    path: "/file/:fileId",
    element: <FilePage />,
    loader: authenticationLoader

  },
  
  // Group management routes (OWNER only)
  {
    path: "/updategroup/:groupId",
    element: <UpdateGroup />,
    loader: settingsLoader
  },
  {
    path: "/group/:groupId/updatemember/:memberId",
    element: <UpdateMember />,
    loader: settingsLoader
  },
  
  // Group activity routes
  {
    path: "/memberupdate/:groupId",
    element: <MemberUpdate />,
    loader: groupActivityLoader
  }]}
]);

export default function Layout() {

  
  return <RouterProvider router={router} />;
}


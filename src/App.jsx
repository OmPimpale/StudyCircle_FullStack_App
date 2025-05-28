import { BrowserRouter, Route, Routes } from 'react-router'
import './App.css'
import AboutUs from './pages/toall/AboutUs'
import Home from './pages/toall/Home'
import Login from './pages/auth/Login'
import Dashboard from './pages/user/Dashboard'
import CreateAccount from './pages/auth/CreateAccount'
import BookingPage from './pages/user/BookingPage'
import PaymentPage from './pages/user/PaymentPage'
import SubjectPage from './pages/toall/SubjectPage'
import GoalSettingPage from './pages/user/GoalSettingPage'
import ProgressTrackingPage from './pages/user/ProgressTrackingPage'
import HelpCenterPage from './pages/toall/HelpCenterPage'
import EarningsPage from './pages/user/EarningsPage'
import FeedbackPage from './pages/user/FeedbackPage'
import TutorProfilePage from './pages/toall/TutorProfilePage'
import ResourcesPage from './pages/toall/ResourcesPage'

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/about' element={<AboutUs />} />
          <Route path='/login' element={<Login />} />
          <Route path='/singUp' element={<CreateAccount />} />
          <Route path='/subjects/:subjectName' element={<SubjectPage />} />
          <Route path='/dashboard' element={<Dashboard />} />
          <Route path='/book/:tutorId' element={<BookingPage />} />
          <Route path='/goals' element={<GoalSettingPage />} />
 <Route path='/progress' element={<ProgressTrackingPage />} />
 <Route path='/help' element={<HelpCenterPage />} />
          <Route path='/feedback' element={<FeedbackPage />} />
          <Route path='/earnings' element={<EarningsPage />} />
          <Route path='/tutors/:tutorId' element={<TutorProfilePage />} />
          <Route path='/payment' element={<PaymentPage />} />
          <Route path='/resources' element={<ResourcesPage />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App

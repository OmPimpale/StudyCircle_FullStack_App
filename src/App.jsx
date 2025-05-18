import { BrowserRouter, Route, Routes } from 'react-router'
import './App.css'
import AboutUs from './pages/toall/AboutUs'
import Home from './pages/toall/Home'
import Login from './pages/auth/Login'
import CreateAccount from './pages/auth/CreateAccount'

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/about' element={<AboutUs />} />
          <Route path='/login' element={<Login />} />
          <Route path='/singUp' element={<CreateAccount />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App

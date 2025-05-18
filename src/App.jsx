import { BrowserRouter, Route, Routes } from 'react-router'
import './App.css'
import AboutUs from './pages/toall/AboutUs'
import Home from './pages/toall/Home'

function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/about' element={<AboutUs />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App

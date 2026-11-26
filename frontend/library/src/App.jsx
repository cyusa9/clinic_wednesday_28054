import { Routes, Route } from 'react-router'
import LibraryMain from './components/LibraryMain'
import Office from "./components/office"
import OfficeList from "./components/OfficeList"
import LibraryLogin from "./components/LIbraryLogin"



function App() {
  return (
   <>
   
    <Routes>
        <Route path="/" element={<LibraryLogin/>}/>
        <Route path="/office" element={<Office/>}/>
        <Route path="/offices" element={<OfficeList/>}/>
        <Route path="/main" element={<LibraryMain/>}/>
      </Routes>
    </>
  )
  
}

export default App

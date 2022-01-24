import React,{useState} from 'react';
import logo from './logo.svg';
import './App.css';
import Topbar from './components/topbar/Topbar';
import Sidebar from './components/sidebar/Sidebar';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Switch } from '@mui/material';
import Customers from './pages/Customers';



function App() {

  return (
    <div className="App">

    <Router>

      <Topbar/>
       <div className="container">
          <Sidebar/>
        
          <Routes>
            <Route path="/" element ={ <Customers/>}/>
             <Route path="/customers" element ={ <Customers/>}/>

          </Routes>
      </div>

    </Router>
      
    </div>
  );
}

export default App;

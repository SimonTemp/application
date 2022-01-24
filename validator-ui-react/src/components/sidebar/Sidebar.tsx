import React from 'react'
import './sidebar.css'
import LineStyle from '@material-ui/icons/LineStyle';
import ContactsIcon from '@material-ui/icons/ContactPhoneRounded';
import { Link } from "react-router-dom";


function Sidebar() {
    return (
        <div className='sidebar'>
              <div className="sidebarWrapper">

                   <div className="sidebarMenu">
                        <h3 className="sidebarTitle">Dashboard</h3>
                         <ul className="sidebarList">
                             <Link to="/" className="link">
                                <li className="sidebarListItem active">
                                    <LineStyle className="sidebarIcon" />
                                    Home
                                </li>
                            </Link>
                             <Link to="/customers" className="link">
                             <li className="sidebarListItem">
                                <ContactsIcon className="sidebarIcon" />
                                Customers
                            </li>
                             </Link>
                         </ul>
                   </div>
              </div>
        </div>
    )
}

export default Sidebar

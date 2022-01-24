import React from 'react'
import './topbar.css'
import ContactPhoneRounded from '@material-ui/icons/ContactPhoneRounded';


const Topbar: React.FC = () => {
    return (
        <div className ='topbar'>
            <div className="topbarWrapper">
                <div className="topLeft">

                    <span className="logo">
                        Jumia
                    </span>
                </div>

                <div className="center">
                    Phone Validation Service
                </div>
                
                <div className="topRight">
                    <ContactPhoneRounded/>
                </div>
            </div>

        </div>
    )
}

export default Topbar

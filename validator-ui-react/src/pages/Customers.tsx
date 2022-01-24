import React,{ useState,useEffect } from 'react'
import './customer.css'
import api from '../api/customerService'
import MaterialTable, { Column } from "@material-table/core";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";


 interface ICustomer{
            id: number,
            name: string,
            phone: string
            
 }

function Customers() {

    
    const [customers,setCustomer] = useState<ICustomer[]>([]);
    const [errorMessages, setErrorMessages] = useState([]);
    const [iserror, setIserror] = useState(false);
    const [country,setCountry] = useState<string>()
    const [status,setStatus] = useState<string>()

    const columns:Array<Column<ICustomer>> = [

        { title: "Name", field: "name" },
        { title: "Phone", field: "phone" },
    ]

    const countries = ['CAMEROON','ETHIOPIA','MOROCCO','MOZAMBIQUE','UGANDA'];
    const statusList = ['VALID','INVALID']

    //Retrieve Customers

      const retrieveCustomers = async () => {
            const response = await api.get("/v1/customer/customers");
            return response.data;
      };

       const retrieveCustomersByFilter = async () => {

            let query =''
            if(country){
               query = `country=${country}`
            }

            if(status){
                query += `Status=${status}`
            }

            if(country && status){

                query = `Status=${status}&country=${country}`
            }
            console.log("Query is",query)
            const response = await api.get("/v1/customer/customersFilter?" + query);
            return response.data;
       };

      useEffect(() => {
    
        const getAllCustomers = async () => {
        const allCustomers = await retrieveCustomers();
            if (allCustomers){
                
                setCustomer(allCustomers);
            }
        };

      //  getAllCustomers();
    },[]);


    const handleRowAdd = (newData: ICustomer, resolve: any) => {

        let errorList = []
        if (newData.name === "") {
        errorList.push("Try Again, You didn't enter the name field")
        }
        if (newData.phone === "") {
        errorList.push("Try Again, You didn't enter the Phone field")
        }    
        
        if (errorList.length < 1) {
             

         api.post(`/v1/customer`, newData)
            .then(response => {
           
                console.log(newData)
                customers.push(newData);
                setCustomer(customers);
                resolve()
                setErrorMessages([])
                setIserror(false)
            })
            .catch(error => {
                //setErrorMessages(["Cannot add data. Server error!"])
                setIserror(true)
                resolve()
            })
        }
    }

    const onCountryChange = async (e: any) => {

        const name = e.target.name;
        const value = e.target.value;

        console.log(name)
        if(name === 'country'){
            setCountry(value);
           
        }

        if(name === 'status'){
            setStatus(value)
        console.log("helloo")
        }
        
        console.log("changed", e.target.value)
    }

     useEffect(() => {
            
           console.log("status    --------",status)

            const results = async () => {

                const allCustomers =  await retrieveCustomersByFilter();
                console.log("All Cutomers",allCustomers)
                setCustomer(allCustomers);
                console.log("gtgtg",country)
            }

            results()
            }, [country,status])
   
    return (
        <div className="customer">
            
            <div className="filter">
                
                <div className="country">
                    <b>Country Filter &nbsp;&nbsp;</b> 
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={country}
                        label="Age"
                        name="country"
                        onChange={onCountryChange}
                    >

                        {countries.map(c => {
                            return (
                            <MenuItem value={c}>{c}</MenuItem>
                            )
                        })}
                        
                    </Select>
                </div>
                &nbsp;&nbsp;

             <div className="country">
                    <b>Phone Status &nbsp;&nbsp;</b> 
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={country}
                        label="Age"
                        onChange={onCountryChange}
                        name="status"
                    >

                        {statusList.map(c => {
                            return (
                            <MenuItem value={c}>{c}</MenuItem>
                            )
                        })}
                        
                    </Select>
             </div>
            </div>
             <MaterialTable 
               title="Customers Contacts"
               columns={columns} 
               data={customers} 
               options={{
                headerStyle: { borderBottomColor: 'red', borderBottomWidth: '3px', fontFamily: 'verdana' },
                actionsColumnIndex: -1
               }}

               editable={{
                    onRowAdd: (newData) =>
                        new Promise((resolve) => {
                           handleRowAdd(newData, resolve)
                        })
               }}
               
            />
        </div>
    )
}

export default Customers

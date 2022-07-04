import { useEffect, useState } from "react";
import DashboardService from "../service/DashboardService";
import DashboardComponent from "./DashboardComponent";

function Dashboard(){
    const [components, setComponents] = useState([])
    const getAllItems = () => {
        DashboardService.getAllItems()
        .then(response => {
            console.log(response.data);
            setComponents(response.data)
        })
    }
    useEffect(() => {
        getAllItems();
    }, [])
    return(
        <div className="main-container">
            <h1>You have reached dashboard</h1>
            <DashboardComponent componentList={components} />
        </div>
    )
}

export default Dashboard;
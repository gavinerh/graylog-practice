import { useEffect, useState } from "react";
import DashboardService from "../service/DashboardService";

function Dashboard(){
    // const [components, setComponents] = useState([])
    // useEffect(() => {
    //     DashboardService.getAllItems()
    //     .then(response => {
    //         console.log(response.data);

    //     })
    // })
    return(
        <div className="main-container">
            <h1>You have reached dashboard</h1>
        </div>
    )
}

export default Dashboard;
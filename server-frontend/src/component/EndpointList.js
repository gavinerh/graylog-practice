import { useEffect, useState } from "react";
import EndpointService from "../service/EndpointService";
import '../styling/modifyDashboard.css';
import EndpointItems from "./EndpointItems";

function EndpointList() {

    const [list, setList] = useState([])

    const getAllItems = () => {
        EndpointService.getEndpoints()
        .then(response => {
            setList(response.data)
        })
        .catch(error => console.log(error));
    }

    useEffect(()=>{
        getAllItems();
    }, [])




    return (
        <div>
            <div className="main-container">
                <h3>List of endpoints</h3>
                <EndpointItems list={list} getAllItemsHandler={getAllItems} />
            </div>
        </div>
    )
}

export default EndpointList;
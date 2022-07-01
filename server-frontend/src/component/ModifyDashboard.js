import { useEffect, useState } from "react";
import DashboardService from "../service/DashboardService";
import '../styling/modifyDashboard.css';
import DashboardItems from "./DashboardItems";

function ModifyDashboard() {
    const [visibility, setVisibility] = useState(true);
    // const [details, setDetails] = useState({
    //     queryMessage: "",
    //     time: "",
    //     displayMessage: ""
    // })
    const [queryMessage, setQueryMessage] = useState("");
    const [time, setTime] = useState('');
    const [displayMessage, setDisplayMessage] = useState("");

    const getAllItems = () => {
        DashboardService.getAllItems()
        .then(response => {
            console.log(response.data);
            setList(response.data)
        })
        .catch(error => console.log(error));
    }

    const [list, setList] = useState([])

    useEffect(()=>{
        getAllItems();
    }, [])

    const clearFormEntry = () => {
        console.log("clearformentry called");
        // setDetails(() => {
        //     return {
        //         queryMessage: "",
        //         time: "",
        //         displayMessage: ""
        //     }
        // })
        setQueryMessage("");
        setTime("");
        setDisplayMessage("");
    }
    const submitHandler = (event) => {
        event.preventDefault();
        DashboardService.newItemCreation(queryMessage, time, displayMessage)
        .then(response => {
            console.log(response);
            getAllItems();
        })
        .catch(error => {
            console.log(error)
        })
        clearFormEntry();
    }

    // const inputChangeHandler = (event) => {
    //     setDetails((prevState) => {
    //         return{
    //             ...prevState,
    //             [event.target.name]: event.target.value
    //         }
    //     })
    // }
    const timeChangeHandler = (event) => {
        setTime(event.target.value);
    }
    const queryMessageHandler = (event) => {
        setQueryMessage(event.target.value);
    }

    const displayMessageHandler = (event) => {
        setDisplayMessage(event.target.value);
    }

    const visibilityHandler = () => {
        setVisibility(!visibility)
    }
    return (
        <div>
            <div className="main-container">
                <form onSubmit={submitHandler}>
                    {visibility ?
                        <div className="new-item-controls" style={{ width: "500px" }}>
                            <div className="new-item-control">
                                <h3>Create dashboard item</h3>
                            </div>
                            <div className="new-item-control">
                                <label>Message to query elasticsearch: </label>
                                <input type="text" value={queryMessage} name="queryMessage" onChange={queryMessageHandler} />
                            </div>
                            <div className="new-item-control">
                                <label>How far back to search (in minutes):</label>
                                <input type="number" value={time} name="time" onChange={timeChangeHandler}/>
                            </div>
                            <div className="new-item-control">
                                <label>Message to display to user:</label>
                                <input type="text" value={displayMessage} name="displayMessage" onChange={displayMessageHandler}/>
                            </div>
                            <div className="new-item-control">
                                <button type="submit">Submit</button>
                                <button onClick={visibilityHandler}>Cancel</button>
                            </div>
                        </div> :
                        <div className="new-item-control" style={{ margin: "0px auto" }}>
                            <button className="new-item" onClick={visibilityHandler}>Add Dashboard item</button>
                        </div>}
                </form>
            </div>
            <div className="main-container">
                <h3>List of created components</h3>
                <DashboardItems list={list} />
            </div>
        </div>
    )
}

export default ModifyDashboard;
import { useEffect, useState } from "react";
import UdpService from "../service/UdpService";
import GeneralList from "./GeneralList";
import OneValueInputForm from "./OneValueInputForm";
import UdpList from "./UdpList";

function ModifyUdpStreaming(){
    const formTitle = "Create new Udp Streaming port"
    const inputName = "Enter port number:"
    const listTitle = "List of Udp Stream ports open";
    const [visibility, setVisibility] = useState(false);
    const [portNum, setPortNum] = useState("");
    const [portArr, setPortArr] = useState([]);

    const changeHandler = (event) => {
        setPortNum(event.target.value);
    }

    const getPorts = () => {
        UdpService.getPorts()
        .then(response => {
            setPortArr(response.data);
        })
    }

    const formSubmitHandler = (event) =>{
        event.preventDefault();
        UdpService.createPort(portNum)
        .then(response => {
            getPorts();
            setPortNum("")
            setVisibility(!visibility)
        })
        .catch(error => {
            console.log(error);
        })
    }

    useEffect(() => {
        getPorts();
    }, [])

    const visibilityHandler = () =>{
        setVisibility(!visibility);
    }
    const deleteHandler = (port) =>{
        UdpService.deletePort(port)
        .then(response => {
            getPorts()
        })
    }
    return(
        <div>
            <div className="main-container">
                {visibility ? 
                <OneValueInputForm formTitle={formTitle} 
                                inputName={inputName} 
                                inputValue={portNum} 
                                inputChangeHandler={changeHandler}
                                submitHandler={formSubmitHandler} 
                                visibility={visibilityHandler}/>
                                : 
                                <div className="new-item-control" style={{ margin: "0px auto" }}>
                                <button className="new-item" onClick={visibilityHandler}>Add new Udp Stream port</button>
                            </div>
                }
            </div>
            <div className="main-container">
                <UdpList itemList={portArr}
                        listTitle={listTitle} 
                        deleteHandler={deleteHandler}/>
            </div>
        </div>
    )
}

export default ModifyUdpStreaming;
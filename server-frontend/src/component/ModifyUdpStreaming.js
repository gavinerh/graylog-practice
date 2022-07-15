import { useEffect, useState } from "react";
import UdpService from "../service/UdpService";
import GeneralList from "./GeneralList";
import UdpCreationForm from "./UdpCreationForm";
import UdpList from "./UdpList";

function ModifyUdpStreaming(){
    const formTitle = "Create new Udp Streaming port"
    const inputName = "Enter port number:"
    const listTitle = "List of Udp Stream ports open";
    const [visibility, setVisibility] = useState(false);
    const [portNum, setPortNum] = useState("");
    const [portArr, setPortArr] = useState([]);
    const [kafkaTopic, setKafkaTopic] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const changeHandler = (event) => {
        setPortNum(event.target.value);
    }

    const topicChangeHandler = (event) => {
        setKafkaTopic(event.target.value);
    }

    const getPorts = () => {
        UdpService.getPorts()
        .then(response => {
            setPortArr(response.data);
        })
    }

    const formSubmitHandler = (event) =>{
        event.preventDefault();
        if(portNum === "" || kafkaTopic === ""){
            setErrorMessage("Both fields must be filled");
            return;
        }
        if (portNum <= 0){
            setErrorMessage("Port number cannot be negative")
            return;
        }
        UdpService.createPort(portNum, kafkaTopic)
        .then(response => {
            getPorts();
            setPortNum("");
            setKafkaTopic("");
            setErrorMessage("");
            setVisibility(!visibility)
        })
        .catch(error => {
            console.log(error);
            setErrorMessage("Port number is already taken");
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
            getPorts();
        })
    }
    return(
        <div>
            <div className="main-container">
                {visibility ? 
                <UdpCreationForm formTitle={formTitle} 
                                inputName={inputName} 
                                inputValue={portNum} 
                                inputTopic={kafkaTopic}
                                inputTopicHandler={topicChangeHandler}
                                inputChangeHandler={changeHandler}
                                submitHandler={formSubmitHandler} 
                                visibility={visibilityHandler}
                                errorMessage={errorMessage}/>
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
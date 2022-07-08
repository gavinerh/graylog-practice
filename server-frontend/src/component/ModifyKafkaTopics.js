import CreateKafkaTopicForm from "./CreateKafkaTopicForm";
import '../styling/modifyDashboard.css';
import { useState } from "react";
import { useEffect } from "react";
import axios from "axios";
import KafkaService from "../service/KafkaService";
import DashboardComponent from "./DashboardComponent";
import GeneralList from "./GeneralList";

function ModifyKafkaTopics(props) {
    const formTitle = "Create new Kafka Topic";
    const formInputName = "Name of Topic:";
    const kafkaListTitle = "List of Kafka Topics";
    const [visibility, setVisibility] = useState(false);
    const [topicList, setTopicList] = useState([]);
    const visibilityHandler = () => {
        setVisibility(!visibility)
    }
    const getTopics = () => {
        KafkaService.getTopics()
            .then(response => {
                setTopicList(response.data);
            })
    }
    useEffect(() => {
        getTopics()
    }, [])
    const completeTopicCreationHandler = () => {
        setVisibility(!visibility);
        getTopics();
    }
    return (
        <div>
            <div className="main-container">
                {visibility ?
                    <CreateKafkaTopicForm setVisibilityHandler={visibilityHandler} 
                                        completeTopicCreation={completeTopicCreationHandler}
                                        formTitle={formTitle} formInputName={formInputName}
                                        kafkaListTitle={kafkaListTitle} />
                    :
                    <div className="new-item-control" style={{ margin: "0px auto" }}>
                        <button className="new-item" onClick={visibilityHandler}>Add new Topic</button>
                    </div>
                }
            </div>

            <div className="main-container">
                <div>
                    <GeneralList key={null} itemList={topicList} listTitle={kafkaListTitle} />
                </div>
            </div>
        </div>

    )
}

export default ModifyKafkaTopics;
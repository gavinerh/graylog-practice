import { useState } from "react";
import KafkaService from "../service/KafkaService";

function CreateKafkaTopicForm(props) {
    const [name, setName] = useState("")
    const [errorMessage, setErrorMessage] = useState("");
    const nameChangeHandler = (event) => {
        setName(event.target.value)
    }
    const changeVisibilityHandler = () => {
        props.setVisibilityHandler();
    }
    const submitHandler = (event) => {
        event.preventDefault();
        if(name === ""){
            setErrorMessage("Field must be filled");
            return;
        }
        KafkaService.isTopicCreated(name)
            .then(response => {
                console.log("successfully created topic");
                setErrorMessage("")
                KafkaService.createTopic(name)
                .then(response => {
                    props.completeTopicCreation();
                })
            })
            .catch(error => {
                setErrorMessage("Topic Already Created");
            })
        


    }
    return (
        <div>
            <form onSubmit={submitHandler}>
                <div className="new-item-controls" style={{ width: "500px" }}>
                    <div className="new-item-control">
                        <h3>{props.formTitle}</h3>
                    </div>
                    <div className="new-item-control">
                        <label>{props.formInputName}</label>
                        <input type="text" value={name} onChange={nameChangeHandler} />
                        <div style={{color: "red", fontWeight: "bold"}}>
                            {errorMessage}
                        </div>
                    </div>
                    <div className="new-item-control">
                        <button type="submit">Submit</button>
                        <button onClick={changeVisibilityHandler}>Cancel</button>
                    </div>
                </div>
            </form>
        </div>
    )
}

export default CreateKafkaTopicForm;
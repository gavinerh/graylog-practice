import { useState } from "react";
import UserDataService from "../service/UserDataService";

function Login() {
    const [userCredential, setUserCredential] = useState({
        username: ""
    })
    const usernameChangeHandler = (event) => {
        setUserCredential({
            username: event.target.value,
        })
    }

    const submitHandler = (event) =>{
        event.preventDefault();
        console.log("Submit is called");
        UserDataService.login(userCredential.username)
        .then(response => {
            console.log(response);
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <form>
            <div>
                <label for="username">Username:</label>
                <input id="username" type="text" onClick={usernameChangeHandler} value={userCredential.username} />
                <button type="submit" onClick={submitHandler}>Submit</button>
            </div>
        </form>
    )

}



export default Login;
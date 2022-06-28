import { useState } from "react"
import ClassifyLogsService from "../service/ClassifyLogsService"

function ClassifyLogs(){
    const [dateRange, setDateRange] = useState({
        timeMin: "",
        timeMax: ""
    })

    const minDateChangeHandler = (event) =>{
        setDateRange({
            ...dateRange,
            timeMin: event.target.value
        })
    }

    const maxDateChangeHandler = (event) =>{
        setDateRange({
            ...dateRange,
            timeMax: event.target.value
        })
    }

    const submitHandler = (event) => {
        event.preventDefault();
        ClassifyLogsService.classifyLogs(dateRange.timeMin, dateRange.timeMax)
        .then(response => {
            console.log(response);
        }).catch(error => {
            console.log(error);
        })
    }

    return (
        <form>
            <div>
                <label for="timeMin">Min Date:</label>
                <input id="timeMin" type="date" onInput={minDateChangeHandler} value={dateRange.timeMin}/>
                <label for="timeMax">Max Date:</label>
                <br/>
                <input id="timeMax" type="date" onInput={maxDateChangeHandler} value={dateRange.timeMax}/>
                <button type="submit" onClick={submitHandler}>Submit</button>
            </div>
        </form>
    )
}


export default ClassifyLogs;

import { useEffect, useState } from "react"
import ClassifyLogsService from "../service/ClassifyLogsService"
import '../styling/main.css';

function ClassifyLogs() {
    const [logArr, setLogArr] = useState([]);
    const [dateRange, setDateRange] = useState({
        timeMin: "",
        timeMax: ""
    })
    useEffect(()=>{
        ClassifyLogsService.getLogs()
        .then(response => {
            setLogArr(response.data.results)
            console.log(response.data.results);
        })
    }, []);


    const minDateChangeHandler = (event) => {
        setDateRange({
            ...dateRange,
            timeMin: event.target.value
        })
    }

    const maxDateChangeHandler = (event) => {
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
        <div className="main-container">
            <form>
                <table className="date-picker">
                    <tr>
                        <td><label for="timeMin">Min Date:</label></td>
                        <td className="column-spacing"></td>
                        <td><label for="timeMax">Max Date:</label></td>
                    </tr>
                    <tr>
                        <td><input className="date-input" id="timeMin" type="date" onInput={minDateChangeHandler} value={dateRange.timeMin} /></td>
                        <td className="column-spacing"></td>
                        <td><input className="date-input" id="timeMax" type="date" onInput={maxDateChangeHandler} value={dateRange.timeMax} /></td>
                        <td className="column-spacing"></td>
                        <td><button type="submit" onClick={submitHandler} className="button-submit">Submit</button></td>
                    </tr>
                </table>
                <i class="fa-solid fa-house-chimney"></i>
            </form>
            <hr />
            <div>
                {/* <Logs /> */}
            </div>
        </div>

    )
}


export default ClassifyLogs;

import { useEffect, useState } from "react"
import ClassifyLogsService from "../service/ClassifyLogsService"
import '../styling/main.css';
import TimeRangeSetting from "./TimeRangeSetting";
import Logs from "./Logs";

function ClassifyLogs() {
    const [logArr, setLogArr] = useState([]);
    const [dateRange, setDateRange] = useState({
        timeMin: "",
        timeMax: ""
    })
    const [returnedDateRange, setReturnedRange] = useState({
        dateMin: "",
        dateMax: ""
    })

    const [dateError, setDateError] = useState("");

    // the dependency array must stay empty so the rendering only happen once
    useEffect(() => {
        ClassifyLogsService.getLogs()
            .then(response => {
                setLogArr(response.data.results)
                setReturnedRange({
                    dateMin: response.data.timeMin,
                    dateMax: response.data.timeMax
                })
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
        setDateError("");
        if (new Date(dateRange.timeMax) <= new Date(dateRange.timeMin)) {
            setDateError("Upper time limit is less than or similar to lower time limit");
            return;
        }
        let now = new Date().toDateString().split("T")[0];
        let nowDate = new Date(now);
        if (new Date(dateRange.timeMin) >= nowDate) {
            setDateError("Time range selected is out of range");
            return;
        }
        ClassifyLogsService.classifyLogs(dateRange.timeMin, dateRange.timeMax)
            .then(response => {
                ClassifyLogsService.getLogs()
                    .then(response => {
                        setReturnedRange({
                            dateMin: response.data.timeMin,
                            dateMax: response.data.timeMax
                        })
                        setLogArr(response.data.results);
                        setDateRange({
                            timeMax: "",
                            timeMin: ""
                        })
                    })
            }).catch(error => {
                console.log(error);
            })
    }



    return (
        <div style={{height: "100%"}}>
            <div className="main-container">
                <form>
                    <table className="date-picker">
                        <tr>
                            <td>Min Date: </td>
                            <td className="column-spacing"></td>
                            <td>Max Date:</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><input className="date-input" id="timeMin" type="date" onInput={minDateChangeHandler} value={dateRange.timeMin} /></td>
                            <td className="column-spacing"></td>
                            <td><input className="date-input" id="timeMax" type="date" onInput={maxDateChangeHandler} value={dateRange.timeMax} /></td>
                            <td className="column-spacing"></td>
                            <td><button type="submit" onClick={submitHandler} className="button-submit">Submit</button></td>
                        </tr>
                    </table>
                </form>
                {dateError ?
                    <div>
                        {dateError}
                    </div>
                    : <div></div>}
                <hr />
                <div>
                    <TimeRangeSetting returnedDateRange={returnedDateRange} />
                    <Logs logs={logArr} />
                </div>

            </div>
        </div>

    )
}


export default ClassifyLogs;

import Log from "./Log";

function Logs(props) {
    const logs = props.logs;
    return (
        <div className="logs-container">
            {props.logs.map(log => <Log message={log} />)}
        </div>
    )
}

export default Logs;
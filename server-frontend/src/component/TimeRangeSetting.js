

function TimeRangeSetting(props){
    const dateMax = props.returnedDateRange.dateMax;
    const dateMin = props.returnedDateRange.dateMin;
    const displayProps = () => {
        console.log(props)
    }
    return(
        <div>
            <h2 style={{margin:"20px", textAlign: "left"}}>Searched Result</h2>
            <h3 style={{fontWeight:"normal", margin: "20px", textAlign: "left"}}>Date Range: {dateMin} - {dateMax}</h3>
            {/* <table className="date-picker">
                <tr>
                    <td><h3 style={{margin:0}}>Min Date:</h3></td>
                    <td className="column-spacing"></td>
                    <td><h3 style={{margin:0}}>Max Date:</h3></td>
                </tr>
                <tr>
                    <td>{dateMin}</td>
                    <td className="column-spacing"></td>
                    <td>{dateMax}</td>
                </tr>
            </table> */}
            {/* <hr style={{border: "dotted 1px", borderStyle: "none none dotted", margin: 0}}/> */}
        </div>
    )
}

export default TimeRangeSetting;


function TimeRangeSetting(props){
    const dateMax = props.returnedDateRange.dateMax;
    const dateMin = props.returnedDateRange.dateMin;
    const displayProps = () => {
        console.log(props)
    }
    return(
        <div style={{border: "1px solid gray", backgroundColor: "rgb(242, 190, 190)"}}>
            <h2 style={{margin:"20px", textAlign: "left"}}>Searched Result</h2>
            <h3 style={{fontWeight:"normal", margin: "20px", textAlign: "left"}}>Date Range: {dateMin} - {dateMax}</h3>
        </div>
    )
}

export default TimeRangeSetting;
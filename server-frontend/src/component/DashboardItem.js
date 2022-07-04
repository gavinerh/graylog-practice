function DashboardItem(props){

    
    const deleteHandler = () => {
        props.itemDeleteHandler(props.item._id);
    }

    return(
        <div className="item">
            <table style={{textAlign: "left"}}>
                <tr>
                    <td>Id:</td>
                    <td>{props.item._id}</td>
                </tr>
                <tr>
                    <td>Query:</td>
                    <td>{props.item._source.queryMessage}</td>
                </tr>
                <tr>
                    <td>Time</td>
                    <td>{props.item._source.time}</td>
                </tr>
                <tr>
                    <td>Display message:</td>
                    <td>{props.item._source.displayMessage}</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <button  style={{width: "50%"}}>Modify</button>
                        <button onClick={deleteHandler} style={{width: "50%"}}>Delete</button>
                    </td>
                </tr>
            </table>
        </div>
    )
}

export default DashboardItem;
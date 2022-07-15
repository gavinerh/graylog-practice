function UdpItem(props){
    const deleteHandler = () =>{
        props.deleteHandler(props.item.port);
    }
    return(
        <table style={{width: "80%", padding: "10px", margin: "10px auto"}}>
            <tr>
                <td><h4 style={{display: "inline"}}>Port:</h4> {props.item.port}</td>
                <td><h4 style={{display: "inline"}}>Kafka Topic:</h4> {props.item.topicName}</td>
                <td><button onClick={deleteHandler}>Delete</button></td>
            </tr>
        </table>
    )
}

export default UdpItem;
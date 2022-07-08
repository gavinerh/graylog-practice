function UdpItem(props){
    const deleteHandler = () =>{
        props.deleteHandler(props.item);
    }
    return(
        <table style={{width: "80%", padding: "20px", margin: "10px auto"}}>
            <tr>
                <td><h4 style={{display: "inline"}}>Port:</h4> {props.item}</td>
                <td><button onClick={deleteHandler}>Delete</button></td>
            </tr>
        </table>
    )
}

export default UdpItem;
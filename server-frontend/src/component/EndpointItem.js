function EndpointItem(props) {
    console.log(props.item.name)

    return (
        <div className="item">
            <table style={{ textAlign: "left" }}>
                <tr>
                    <td>Endpoint:</td>
                    <td>{props.item.name}</td>
                </tr>
                <tr>
                    <td>Method:</td>
                    <td>{props.item.method}</td>
                </tr>
            </table>
        </div>
    )
}

export default EndpointItem;
import UdpItem from "./UdpItem";

function UdpList(props) {
    console.log(props.itemList);
    return (
        <div>
            <h2>{props.listTitle}</h2>
            <div className="logs-container">
                <div>
                    {props.itemList.map(item => <UdpItem key={item} item={item} deleteHandler={props.deleteHandler} />)}
                </div>
            </div>

        </div>

    )
}

export default UdpList;
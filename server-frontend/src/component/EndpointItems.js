import EndpointItem from "./EndpointItem";

// contains a list of dashboard items
function EndpointItems(props){
    const list = props.list;

    return(
        <div>
            {list.map(item => <EndpointItem item={item}/>)}
        </div>
    )
}

export default EndpointItems;
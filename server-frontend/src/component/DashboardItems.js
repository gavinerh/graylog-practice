import DashboardItem from "./DashboardItem";

// contains a list of dashboard items
function DashboardItems(props){
    const displayResponse = () => {
        console.log("From componentList");
        console.log(props.list)
    }
    const list = props.list;
    return(
        <div>
            {list.map(item => <DashboardItem item={item} />)}
        </div>
    )
}

export default DashboardItems;
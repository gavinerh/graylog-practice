import DashboardService from "../service/DashboardService";

import DashboardItem from "./DashboardItem";

// contains a list of dashboard items
function DashboardItems(props){
    const list = props.list;

    const deleteItemHandler = (id) =>{
        DashboardService.deleteItem(id)
        .then(response => {
            console.log(response)
        })
        props.getAllItemsHandler()
    }
    return(
        <div>
            {list.map(item => <DashboardItem key={item.id} item={item} itemDeleteHandler={deleteItemHandler} />)}
        </div>
    )
}

export default DashboardItems;
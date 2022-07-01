function DashboardItem(props){
    return(
        <div>
            <p>{props.item.id}</p>
            <p>{props.item.queryMessage}</p>
        </div>
    )
}

export default DashboardItem;
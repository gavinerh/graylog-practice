import GeneralListItem from "./GeneralListItem";

function GeneralList(props) {
    return (
        <div>
            <h2>{props.listTitle}</h2>
            <div className="logs-container">
                {props.itemList.map(item => <GeneralListItem message={item} />)}
            </div>
        </div>

    )
}

export default GeneralList;
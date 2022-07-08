import '../styling/dashboardComponent.css';

function DashboardComponent(props) {

    const componentList = props.componentList;
    return (
        <div className='component-container'>
            {componentList.map(component => {
                <div>
                    {component}
                </div>
            })}
        </div>
    )
}

export default DashboardComponent;
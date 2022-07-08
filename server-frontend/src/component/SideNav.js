import '../styling/sideNav.css';
import { Link } from 'react-router-dom';

function SideNav(){
    return(
        <nav className="side-nav">
            <ul>
                <li><Link className='link-tag' to="/">Dashboard</Link></li>
                <li><Link className='link-tag' to="/modifyDashboard">Modify Dashboard</Link></li>
                <li><Link className='link-tag' to="/classification">View log Classifications</Link></li>
                <li><Link className='link-tag' to="/modifyKafkaTopics">List of Kafka Topics</Link></li>
                <li><Link className='link-tag' to="/modifyUdpStreaming">Modify Udp Streaming</Link></li>
            </ul>
        </nav>
    )
}

export default SideNav;
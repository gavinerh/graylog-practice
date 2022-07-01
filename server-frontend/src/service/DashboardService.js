import axios from "axios";

class DashboardService{
    url = "http://localhost:8082/dashboardItem"
    
    newItemCreation(queryMessage, time, displayMessage) {
        // console.log(details);
        return axios.post(`${this.url}/`, {
            queryMessage,
            time,
            displayMessage
        });
    }

    getAllItems(){
        return axios.get(`${this.url}/`);
    }

}

export default new DashboardService();
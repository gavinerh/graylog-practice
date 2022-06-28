import axios from "axios";

class ClassifyLogsService{
    url = "http://localhost:8081"
    classifyLogs(timeMin, timeMax){
        console.log("timeMin: " + timeMin);
        console.log("timeMax: " + timeMax);
        return axios.get(`${this.url}/reclassify`, {params: {
            timeMin,
            timeMax
        }})
    }

    getLogs(){
        console.log("inside get logs");
        return axios.get(`${this.url}/getLogs`)
    }
}

export default new ClassifyLogsService();
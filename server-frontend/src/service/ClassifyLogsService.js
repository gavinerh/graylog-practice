import axios from "axios";

class ClassifyLogsService{
    url = "http://localhost:10000/javaClassify"
    classifyLogs(timeMin, timeMax){
        console.log("inside classifiyLogsService");
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
import axios from "axios";

class KafkaService{
    url = "http://localhost:10000/dashboard/kafka"
    getTopics(){
        return axios.get(`${this.url}/`)
    }

    isTopicCreated(name){
        return axios.get(`${this.url}/${name}`)
    }

    createTopic(name){
        return axios.post(`${this.url}/`, {
            message: name
        })
    }
}

export default new KafkaService();
import axios from "axios";

class UdpService {
    url = "/udpConnector/udpStream"
    getPorts() {
        return axios.get(`${this.url}/`);
    }

    createPort(port, topic) {
        let address = "0.0.0.0"
        
        return axios.post(`${this.url}/`, {
            port: port,
            address: address,
            topicName: topic
        })
    }

    deletePort(port){
        return axios.delete(`${this.url}/`, {params: {
            port
        }})
    }


}

export default new UdpService();
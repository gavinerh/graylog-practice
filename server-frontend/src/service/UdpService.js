import axios from "axios";

class UdpService {
    url = "/udpConnector/udpStream"
    getPorts() {
        return axios.get(`${this.url}/`);
    }

    createPort(port) {
        let address = "0.0.0.0"
        
        return axios.post(`${this.url}/`, null, {
            params: {
                port,
                address
            }
        })
    }

    deletePort(port){
        return axios.delete(`${this.url}/`, {params: {
            port
        }})
    }


}

export default new UdpService();
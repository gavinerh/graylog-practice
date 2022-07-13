import axios from "axios";

class Endpoints{
    url = "http://localhost:8080/endpoint"
    getEndpoints(){
        return axios.get(`${this.url}/`);
    }

    getEndpointsWithServiceName(){
        return axios.get(`${this.url}/services`);
    }
}

export default new Endpoints();
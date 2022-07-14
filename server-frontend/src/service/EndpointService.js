import axios from "axios";

class EndpointService{
    url = "http://localhost:10000/dashboard/endpoint"
    getEndpoints(){
        return axios.get(`${this.url}/`);
    }

    getEndpointsWithServiceName(){
        return axios.get(`${this.url}/services`);
    }
}

export default new EndpointService();
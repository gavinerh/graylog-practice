import axios from "axios";

class UserDataService{
    
    login(username){
        // const address = process.env.SERVER_ADDRESS || 'localhost'
        // console.log(address);
        // return axios.post('http://' + address + ':8080/authenticate',{
        //     username: username
        // });
        return axios.post('/backend/authenticate',{
            username: username
        });
    }
}

export default new UserDataService();
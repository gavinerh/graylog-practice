# Logging Server

## Project Description
This project main function is to act as a logging server, specifically to receive log messages via 
udp transfer protocol. The core technology uses a graylog server, which is an open-source project 
server that can manipulate and handle log messages. The project is set-up using docker compose 
where the graylog server and other sub-services are running in separate containers. The host machine
is preferably running linux.

# How to install and run the project
1. Edit the `docker-compose.yml` at the following locations:
    * `environment` tag under the `graylog`
service tag for appropriate email configuration and GRAYLOG_ROOT_PASSWORD in SHA hash format
    * Host machine storage location for bind mounts under the `volumes` tag
    * `ports` information under the `graylog` service tag for the appropriate udp socket number to be exposed
2. At the root of the project directory, run `sudo docker-compose up`
3. At the root of the project directory, also run `sudo python3 main.py &` to start the udp broadcast service. 
Information regarding the broadcast information is located in the ports.conf configuration file.
4. Check if the containers are running, type `sudo docker ps` to confirm
5. Once the containers are running, use ssh port forwarding to access web interface of graylog: 
6. Visit the graylog service from local browser, and login with `admin` as user and with the
same password as the one used in the docker-compose.yml file
7. After login, start the udp input stream at the same port number as specified in the docker-compose.yml file
see image: ![image](https://user-images.githubusercontent.com/75064420/174514501-6f905e32-6f00-4ac6-ab0b-e60cf9b09c92.png)
8. Set up input stream:
    * Create new input stream under System -> Inputs
    * Select new input stream type see image: ![image](https://user-images.githubusercontent.com/75064420/174514947-53904f1b-942f-4f6a-8351-7e3250534e02.png)
    * Use the default settings but change the input port number: ![image](https://user-images.githubusercontent.com/75064420/174515211-9c0c3a8f-e30e-49c6-b8bb-55d9365bf92d.png)
9. Set up alerts under the alerts tab
    * Create new notifications:
        * Select from the list of notifications dropdown and enter the appropriate information
    * Create new event definition
        * Select the Filter and Aggregation tab
        * Enter the search query that graylog should trigger an event when such a message is detected
        * At the notifications tab, add new notification that was created in the previous step and click done
        * Click the summary tab and click done
10. Use ssh port forwarding again, but now on another port number to view the custom frontend
web console
    * The custom web-console will portray information or allow custom actions such as:
        * view the list of brute force attacks
        * view the current file classifications 
# How to stop the project
1. Go to the root directory and type `sudo docker-compose down`
2. Also at the root directory, type `./stopBroadcast` to stop udp broadcast

# How to use the project

# Using ssh port forwarding


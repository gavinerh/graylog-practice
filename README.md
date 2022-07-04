# Logging Server

## Overall architecture diagram
![FHT internship architecture](https://user-images.githubusercontent.com/75064420/177126500-411c23c5-f745-4bd0-921d-0c17fccdfca9.jpg)


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
2. Create the containers by building the containers for the services:
    * Build python server
        * `cd flask-server`
        * `sudo docker build -t gavinerh/flask-test-server-6 .`
    * Pull the java-classification-server from remote docker hub
        * `sudo docker pull gavinerh/java-classification-server`
    * (Optional) Build java classification server from source code directly:
        * First: Install maven
        * Second: Check maven is installed
        * Continue to build the container
            * From the root of the project: `cd classification-server`
            * `sudo mvn clean install -DskipTests`
            * `sudo docker build -t gavinerh/java-classification-server .`
    * Build the react frontend server:
        * First: Install npm and node
        * Second: Check npm and node version
        * Continue to build the container:
            * From the root of the project: `cd server-frontend`
            * `sudo npm install`
            * `sudo docker build -t gavinerh/react-test .`
3. At the root of the project directory, run `sudo docker-compose up`
4. `cd udpBroadcaster` to enter the directory udpBroadcaster
5. Type `./startBroadcast` to start the udp broadcast service. 
information regarding the broadcast information is located in the ports.conf configuration file.
6. Check if the containers are running, type `sudo docker ps` to confirm
7. Once the containers are running, use ssh port forwarding to access web interface of graylog at port 9000: 
8. Visit the graylog service from local browser, and login with `admin` as user and with the
same password as the one used in the docker-compose.yml file
9. After login, start the udp input stream at the same port number as specified in the docker-compose.yml file
see image: ![image](https://user-images.githubusercontent.com/75064420/174514501-6f905e32-6f00-4ac6-ab0b-e60cf9b09c92.png)
10. Set up input stream:
    * Create new input stream under System -> Inputs
    * Select new input stream type see image: ![image](https://user-images.githubusercontent.com/75064420/174514947-53904f1b-942f-4f6a-8351-7e3250534e02.png)
    * Use the default settings but change the input port number: ![image](https://user-images.githubusercontent.com/75064420/174515211-9c0c3a8f-e30e-49c6-b8bb-55d9365bf92d.png)
11. Set up alerts under the alerts tab
    * Create new notifications:
        * Select from the list of notifications dropdown and enter the appropriate information
    * Create new event definition
        * Select the Filter and Aggregation tab
        * Enter the search query that graylog should trigger an event when such a message is detected
        * At the notifications tab, add new notification that was created in the previous step and click done
        * Click the summary tab and click done
12. Use ssh port forwarding again, but now using the nginx port number to view the custom frontend
web console at port 10000
    * The custom web-console will portray information or allow custom actions such as:
        * view the list of brute force attacks
        * view the current file classifications 
# How to stop the project
1. Go to the root directory and type `sudo docker-compose down`
2. `cd udpBroadcaster` to enter udpBroadcaster folder, type `./stopBroadcast` to stop udp broadcasting service

# How to use the project

# List of services running
1. Graylog server
    * HTTP and email alerts and notification of brute force attacks
    * UDP stream input listener
2. Elasticsearch
    * Store log messages
4. Mongodb
    * Store graylog user specific server
5. Python flask server
    * Receive http notification from graylog alerts of brute force attack
    * Classify logs depending on set time range
    * Capture event_definition_description values from HTTP notification of graylog and query elasticsearch for the past 15 minutes for new brute force entries, followed by populating the brute force logs file
6. Java backend server
    * provide rest endpoints to
        * list all classifications within a set time range, http calls to python flask server
        * list of all brute force attacks with timestamp
        * trigger logs classifications (http calls to python server)
7. React web console
    * Another web-interface on top of the graylog server to provide most information to user
    * Frontend interface for java backend server
8. Nginx reverse proxy
    * For routing react http calls to java backend server

# Using ssh port forwarding


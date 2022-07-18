# Logging Server

## Overall architecture diagram
![FHT internship architecture](https://user-images.githubusercontent.com/75064420/179473020-171f515c-9bd2-4314-b39d-ccf793e176e5.jpg)


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
2. Download maven and npm
3. Modify the bash file
    * `sudo chmod +x createFolderStructure.sh`
    * `sudo chmod +x buildDockerImages.sh`
3. Create the containers by building the containers for the services:
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
    * ![image](https://user-images.githubusercontent.com/75064420/178925282-7afbe4a4-d4e4-414b-9873-c7de95cda9f3.png)
    * Choose the syslog kafka option as the messages will enter graylog through kafka streams
    * ![image](https://user-images.githubusercontent.com/75064420/178925821-e0d4a259-aacf-4396-b6c7-aff51716db00.png)
    * all the options will be the same, except the title (user defined)
    * change the text under Bootstrap servers and zookeeper address to the ones defined in the image above
    * Also change the Topic filter regex to text shown in image
    * leave the Legacy mode unchecked
    * use similar instructions to create another syslog kafka stream but for this new kafka stream, the Topic filter regex option will be '^classification$'
11. Set the UDP stream ports and creating new kafka topics
    * Visit the react project running at localhost:10000
    * ![image](https://user-images.githubusercontent.com/75064420/178927138-4866d78b-0b70-4ccc-be7a-c812b6a4c9d6.png)
    * at the left sidebar, click on the last option tab 'modify Udp streaming' 
    * click on add new Udp stream port, and enter port number 1515 and submit
    ![image](https://user-images.githubusercontent.com/75064420/178927782-f3107d76-876b-43a9-98e8-0c81abbe7dc0.png)
    * Do the same for port 1516 and 1517 and the final page will look similar to image below
    * ![image](https://user-images.githubusercontent.com/75064420/178928340-8107b588-f22a-44db-ba8f-9cd423fa68fd.png)
    * Continue to create new kafka topic by clicking on the 3 option of the left sidebar 'list of kafka topics'
    * ![image](https://user-images.githubusercontent.com/75064420/178928845-def5c591-a5ac-400a-8f6b-e1b7812b09a8.png)
    * Click on add new topic to create another topic named classification
    * in total, there should be 2 topics created, pfsense and classification
12. Set up alerts under the alerts tab
    * Create new notifications:
        * Select from the list of notifications dropdown and enter the appropriate information
    * Create new event definition
        * Select the Filter and Aggregation tab
        * Enter the search query that graylog should trigger an event when such a message is detected, an example: 'message: "failed password"'
        * ![image](https://user-images.githubusercontent.com/75064420/178931651-69a4e757-fea8-41cd-b63d-ed87658a101e.png)

        * At the notifications tab, add new notification that was created in the previous step and click done
        * Click the summary tab and click done
13. Use ssh port forwarding again, but now using the nginx port number to view the custom frontend
web console at port 10000

# How to stop the project
1. Go to the root directory and type `sudo docker-compose down`

# How to use the project
1. Use the react project running at localhost:10000 to configure the services for graylog server
2. Use the graylog server running at localhost:9000 to configure input streams, events and notifications

# List of services running
1. Graylog server
    * HTTP and email alerts and notification of brute force attacks
    * Kafka stream input listener
2. Elasticsearch
    * Store log messages
4. Mongodb
    * Store graylog user specific server
    * Store configuration information
5. Python flask server
    * Receive http notification from graylog alerts of brute force attack
    * Classify logs depending on set time range
    * Primarily used to query elasticsearch database
6. Java classification server
    * provide rest endpoints to
        * Create classifications within a set time range, trigger http calls to python flask server
            * Endpoint: /javaClassify/reclassify
            * Method: GET
        * Get List of logs classifications based on the duration of search for jmpadm and guacamole servers
            * Endpoint: /javaClassify/getLogs
            * Method: GET
        * trigger combined logs classifications (http calls to python server)
            * Endpoint: /javaClassify/generateClassificationResult
            * Method: GET
7. Kafka message broker server
    * Provide asynchronous message broker service between the microservices
8. Zookeeper server
    * Hold information of the kafka broker, works in tandem with kafka
9. Udp configuration server
    * runs on java
    * receives udp messages and produce messages to kafka broker
    * Create and Route udp port to kafka
        * Endpoint: /udpConnector/udpStream/
        * Method: POST
    * Get all opened udp ports
        * Endpoint: /udpConnector/udpStream/
        * Method: GET
    * Delete udp port
        * Endpoint: /udpConnector/udpStream/
        * Method: DELETE
10. Nginx reverse proxy
    * For routing react http calls to most backend services
    * Act as a reverse proxy for the react server and other backend services
    * Hosting at port 10000
11. Scheduler
    * For scheduling log classification cron jobs for classification results
    * Schedule delete jobs on elasticsearch, to prevent problems of storage reaching limit
    * Get List of scheduled cron jobs:
        * Endpoint: /scheduler/schedule/
        * Method: GET
12. Kafka-Configuration-server
    * Add new Topics in kafka
        * Endpoint: /dashboard/kafka/
        * Method: POST
    * Get List of topics created in kafka
        * Endpoint: /dashboard/kafka/ 
        * Method: GET
13. React web console
    * Another web-interface on top of the graylog server to provide all endpoint information to user
    * Allow user to view log classification according to duration of search
    * Create new kafka topics and view existing kafka topics
    * Create or delete udp ports

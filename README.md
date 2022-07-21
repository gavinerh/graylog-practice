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
2. Download maven
    * Remember to add maven command to the /etc/sudoers file, secure_path
    * ![image](https://user-images.githubusercontent.com/75064420/180113172-eaee0867-b7e0-4257-bfc4-a4d849487088.png)

3. Modify the bash file
    * `sudo chmod +x createFolderStructure.sh`
    * `sudo chmod +x buildDockerImagesWithoutMaven.sh`
    * `sudo chmod +x buildDockerImagesWithMaven.sh`
4. Create the containers by building the containers for the services by choosing ONE of the below choices:
    * Build docker images without maven installed in host machine
        * `./buildDockerImagesWithoutMaven.sh`
    * Build docker images with maven already installed in host machine
        * `./buildDockerImagesWithMaven.sh`
5. After the build process is done, at the root of the project directory, run `sudo docker-compose up -d`
6. Check if the containers are running, type `sudo docker ps` to confirm
7. Once the containers are running, use ssh port forwarding to access web interface of graylog at port 9000 or port 10000 for the react web interface
8. Visit the graylog service from local browser, and login with `admin` as user and with the
same password as the one used in the docker-compose.yml file
9. After login, start by creating a new input stream by choosing the `syslog kafka` option
see image: ![image](https://user-images.githubusercontent.com/75064420/174514501-6f905e32-6f00-4ac6-ab0b-e60cf9b09c92.png)
10. Set up input stream:
    * Create new input stream under System -> Inputs
    * Select new input stream type see image: ![image](https://user-images.githubusercontent.com/75064420/174514947-53904f1b-942f-4f6a-8351-7e3250534e02.png)
    * ![image](https://user-images.githubusercontent.com/75064420/178925282-7afbe4a4-d4e4-414b-9873-c7de95cda9f3.png)
    * Choose the syslog kafka option as the messages will enter graylog through kafka streams
    * ![image](https://user-images.githubusercontent.com/75064420/178925821-e0d4a259-aacf-4396-b6c7-aff51716db00.png)
    * all the options will be the same, except the title (user defined)
    * change the text under Bootstrap servers and zookeeper address to the ones defined in the image above
    * Also change the Topic filter regex to text shown in image, choose any kafka topic name you desire
    * leave the Legacy mode unchecked
    * use similar instructions to create another syslog kafka stream but for this new kafka stream, the Topic filter regex option will be '^classification$' or any name you desire
11. Set the UDP stream ports and creating new kafka topics
    * Visit the react project running at localhost:10000
    * ![image](https://user-images.githubusercontent.com/75064420/178927138-4866d78b-0b70-4ccc-be7a-c812b6a4c9d6.png)
    * at the left sidebar, click on the last option tab 'modify Udp streaming' 
    * click on add new Udp stream port, and enter port number 1515 and any kafka topic name you want and submit (note that the kafka topic name should be same as the one you choose when setting up the graylog kafka input)
    ![image](https://user-images.githubusercontent.com/75064420/179896418-61a09875-1684-460f-b72a-546881a66d7b.png)

    * Do the same for port 1516 and 1517 and the final page will look similar to image above
    * Continue to create new kafka topic by clicking on the third option of the left sidebar 'list of kafka topics'
    * ![image](https://user-images.githubusercontent.com/75064420/178928845-def5c591-a5ac-400a-8f6b-e1b7812b09a8.png)
    * Click on add new topics depending on the name you defined previously when setting the graylog inputs
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
            * Endpoint: java-classification-server:8081/reclassify
            * Method: GET
        * Get List of logs classifications based on the duration of search for jmpadm and guacamole servers
            * Endpoint: java-classification-server:8081/getLogs
            * Method: GET
        * trigger combined logs classifications (http calls to python server)
            * Endpoint: java-classification-server:8081/generateClassificationResult
            * Method: GET
7. Kafka message broker server
    * Provide asynchronous message broker service between the microservices
8. Zookeeper server
    * Hold information of the kafka broker, works in tandem with kafka
9. Udp configuration server
    * runs on java
    * receives udp messages and produce messages to kafka broker
    * Create and Route udp port to kafka
        * Endpoint: udp-configuration-server:8083/udpStream/
        * Method: POST
    * Get all opened udp ports
        * Endpoint: udp-configuration-server:8083/udpStream/
        * Method: GET
    * Delete udp port
        * Endpoint: udp-configuration-server:8083/udpStream/
        * Method: DELETE
10. Nginx reverse proxy
    * For routing react http calls to most backend services
    * Act as a reverse proxy for the react server and other backend services
    * Hosting at port 10000
11. Scheduler
    * For scheduling log classification cron jobs for classification results
    * Schedule delete jobs on elasticsearch, to prevent problems of storage reaching limit
    * Get List of scheduled cron jobs:
        * Endpoint: java-scheduler:8084/schedule/
        * Method: GET
12. Kafka-Configuration-server
    * Add new Topics in kafka
        * Endpoint: java-dashboard-server:8082/kafka/
        * Method: POST
    * Get List of topics created in kafka
        * Endpoint: java-dashboard-server:8082/kafka/ 
        * Method: GET
13. React web console
    * Another web-interface on top of the graylog server to provide all endpoint information to user
    * Allow user to view log classification according to duration of search
    * Create new kafka topics and view existing kafka topics
    * Create or delete udp ports

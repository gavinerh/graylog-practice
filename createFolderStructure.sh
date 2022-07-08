#!/bin/bash
sudo mkdir data
cd data/
sudo mkdir elasticsearch-data2
sudo mkdir graylog-data
sudo mkdir kafka-data
sudo mkdir mongodb-data
sudo mkdir port-data
sudo mkdir zookeeper-data
sudo mkdir zookeeper-log
sudo mkdir python-data
cd python-data/
sudo mkdir conf_files
sudo mkdir logfiles
sudo cp ../../filepaths.conf conf_files
sudo cp ../../query conf_files
cd logfiles/
sudo mkdir combined
sudo mkdir guacamole
sudo mkdir jmpadm
sudo touch error
sudo touch timeRangeData
sudo touch timeRangeSearch
cd combined/
sudo touch logs
sudo touch logs-all

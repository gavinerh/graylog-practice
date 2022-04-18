import json

import requests


def search(uri):
    query = json.dumps({
        "size":10000,
        "query" : {
            "bool": {
                "must": [
                    {
                        "match_phrase": {
                            "message": "ET SCAN Suspicious inbound"
                        }
                    },
                    {
                        "range" : {
                            "timestamp": {
                                "gt": "2022-03-28 00:00:00.000",
                                "lt": "2022-04-18 00:00:00.000"
                            }
                        }
                    }
                ]
            }
        }
    })
    # print(query)
    response = requests.get(uri, data=query, headers={"Content-Type":"application/json"})
    results = json.loads(response.text)
    return results

def exportToCSV(res, title):
    with open('C:\\Users\\gavin\\OneDrive\\Desktop\\logFile\\logs.csv', 'w') as f:
        f.write(title)
        f.write('\n')
        for s in res:
            f.write(s)
            f.write('\n')
        f.close()
    return

def createCSVString(response):
    index = 0;
    while index < len(response):
        timestamp = response[index]['_source']['timestamp']
        message = response[index]['_source']['message'].replace('] ', ',')
        response[index] = message.replace('-> ', ',')
        response[index] += ','
        response[index] += timestamp
        index+=1
    return response

url = "http://localhost:9200/graylog_0/_search"
response = search(url)
response = createCSVString(response['hits']['hits'])
# print(response[0])
title = "\"Source\",\"Message\",\"Priority\",\"Source IP\",\"Destination IP\",\"Timestamp\""
exportToCSV(response[:-10], title)

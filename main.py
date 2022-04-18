import json

import requests


def search(uri):
    query = json.dumps({
        "size":100,
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
                                "gt": "2022-04-15 00:00:00.000"
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

url = "http://localhost:9200/graylog_0/_search"
response = search(url)
print(response['hits']["hits"][0])

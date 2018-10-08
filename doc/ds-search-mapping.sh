#!/bin/bash

SOCKET=$1
VERSION=$2

INDEX=ds-search-v${VERSION}

if [ $# = 0 ] || [ $1 = "help" ]; then
    exit 1
fi

curl -X PUT "${SOCKET}/${INDEX}?pretty" -H 'Content-Type: application/json' -d'
{
    "aliases": {
        "ds-search": {}
    },
    "mappings" : {
        "page" : {
            "properties" : {
                "uri" :             { "type" : "keyword" },
                "title":            {
                                        "type" : "text",
                                        "analyzer": "ik_max_word",
                                        "search_analyzer": "ik_max_word"
                                    },
                "content":          {
                                        "type" : "text",
                                        "analyzer": "ik_max_word",
                                        "search_analyzer": "ik_max_word"
                                    },
                "code":             { "type" : "integer" },
                "crawlDate":        { "type" : "date" },
                "tags":             { "type" : "keyword" }
            }
        }
    }
}
'


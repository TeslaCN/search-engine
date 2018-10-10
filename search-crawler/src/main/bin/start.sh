#!/bin/sh

echo 'Starting Crawler Application...'

LOG_FILE='/var/log/search-engine/CrawlerApplication.log'
CURRENT=`date -d now +'%Y%m%d_%H%M'`
OLD_LOG_FILE="/var/log/search-engine/CrawlerApplication_${CURRENT}.log"

nohup sh run.sh CrawlerApplication > ${LOG_FILE} &
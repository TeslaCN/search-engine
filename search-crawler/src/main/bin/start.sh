#!/bin/sh

echo 'Starting Crawler Application...'

LOG_FILE='/var/log/search-engine/CrawlerApplication.log'
CURRENT=`date -d now +'%Y%m%d_%H%M'`
OLD_LOG_FILE="/var/log/search-engine/CrawlerApplication_${CURRENT}.log"

[ -e ${LOG_FILE} ] && mv ${LOG_FILE} ${OLD_LOG_FILE} && echo "${OLD_LOG_FILE}"

nohup sh run.sh CrawlerApplication > ${LOG_FILE} &
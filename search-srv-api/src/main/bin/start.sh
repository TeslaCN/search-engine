#!/bin/sh

echo 'Starting Search Server...'

LOG_FILE='/var/log/search-engine/SearchApplication.log'
CURRENT=`date -d now +'%Y%m%d_%H%M'`
OLD_LOG_FILE="/var/log/search-engine/SearchApplication_${CURRENT}.log"

[ -e ${LOG_FILE} ] && mv ${LOG_FILE} ${OLD_LOG_FILE} && echo "${OLD_LOG_FILE}"

nohup sh run.sh SearchApplication > ${LOG_FILE} &
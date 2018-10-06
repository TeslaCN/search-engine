#!/bin/bash

for i in `jps | grep -E 'SearchApplication' | awk '{print $1}'`
do
    echo "killing pid  "${i} ;
    kill ${i}
done

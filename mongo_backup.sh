#!/bin/bash

source .env

docker exec mongodb mongodump \
--username=${DB_USERNAME} \
--password=${DB_PASSWORD} \
--authenticationDatabase=admin \
--db=${DB_NAME} \
--archive=/mongo_backup/test_backup.dump


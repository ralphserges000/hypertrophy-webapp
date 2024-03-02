#!/bin/bash

source .env

docker exec mongodb mongorestore \
--username=${DB_USERNAME} \
--password=${DB_PASSWORD} \
--authenticationDatabase=admin \
--nsInclude=${DB_NAME}.* \
--archive=/mongo_backup/test_backup.dump
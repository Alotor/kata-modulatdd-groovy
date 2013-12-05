#!/usr/bin/bash

if [ -f "sample.db" ];
then
    rm sample.db
fi

echo "create table users (username varchar primary_key);" | sqlite3 sample.db
echo "create table user_following(user varchar, following varchar, primary key(user, following));" | sqlite3 sample.db

#!/bin/sh

### generates certificates
## use dns challenge
# sudo certbot certonly --preferred-challenges=dns --email lunacircle4@gmail.com -d freshtuna.site -d *.freshtuna.site
## when renew is not working
# sudo certbot certonly  --email lunacircle4@gmail.com -d freshtuna.site -d *.freshtuna.site
## certifications renew
# sudo certbot renew --force-renewal

### certificates renew cron setting
#0 0 1 * * sudo certbot renew --force-renewal --deploy-hook="docker exec -it web.core.remember sh -c 'nginx -s reload'" >> /var/log/letsencrypt/letsencrypt-renew.log

### without compose
## was
# docker run --detach --network remember --name remember-core --env-file .remember.core.env -p 80:8080 lunacircle4/remember:1.0.1
## db
# docker run --detach --network remember --name remember-core-db --env-file .remember.core.db.env mariadb:10.6.4

### run db container first
#docker-compose up db -d

### in db container, create database and set privilage to user
# mariadb -uroot -ppassword
# create database `core.remember`
# grant all privileges on `core.remember`.* to remember1004@'%' identified by 'USER_PASSWORD';
# flush privileges;

# run other containers
#docker-compose up -d

### init seeds (algorithms, platform)
# docker cp algorithms.sql db.core.remember:/
# docker cp platforms.sql db.core.remember:/
# mariadb -uroot -pROOT_PASSWORD core.remember < platforms.sql
# mariadb -uroot -pROOT_PASSWORD core.remember < platforms.sql

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

## create docker network
# docker network create remember

## in db container, create database and set privilage to user
# mariadb -uroot -ppassword
# create database `core.remember`
# grant all privileges on `core.remember`.* to remember1004@'%' identified by 'USER_PASSWORD';
# flush privileges;

# run containers
docker-compose up -d

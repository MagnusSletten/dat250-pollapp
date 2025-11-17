FROM caddy:alpine

WORKDIR /app

#copy front-end build
COPY src/frontend/my-app/dist /var/www/polling/

#Argument for production caddyfile
ARG CADDY_CONFIG=Caddyfile
COPY caddy/${CADDY_CONFIG} /etc/caddy/Caddyfile

EXPOSE 80
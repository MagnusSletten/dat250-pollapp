FROM caddy:alpine

WORKDIR /app

COPY src/frontend/my-app/dist /var/www/polling/

COPY caddy/Caddyfile /etc/caddy/Caddyfile

EXPOSE 80
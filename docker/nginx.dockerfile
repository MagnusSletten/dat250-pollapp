FROM nginx:alpine

WORKDIR /app

COPY src/frontend/my-app/dist /var/www/polling/

COPY nginx/config.conf /etc/nginx/conf.d/default.conf

EXPOSE 80



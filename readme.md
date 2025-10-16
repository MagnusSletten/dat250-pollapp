# Poll App
This is a fullstack application where users can create and deploy polls

## To start:
### backend: 
```
docker-compose up 
```
The compose consists of three containers: 
- springboot backend which depends on:
- Redis-container for handling caching
- RabbitMQ container for handling the message service. 

For frontend deployment:
```
cd src/frontend/my-app
npm run dev
```



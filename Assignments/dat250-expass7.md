# Assignment 7: Docker

For this assignment I incorporated docker into my poll-app. This makes deployment smoother as everything within the docker containers has a fixed reproducible environment on startup. 

I did opt to go with a docker-compose setup so that launching my other services, namely redis and rabbitmq, was easier. With the current setup, I can run one simple command `docker-compose up` to launch all my services which consists of the three containers and a docker-network to allow communication between the containers. 

## Problems encountered

I have previous experience working with Docker-compose and it made the process easier, but I had some problems with initializing the containers because of order dependency, I need rabbit-mq to be up prior to my poll-backend container. This can be fixed in several ways, but I read online a bit and saw some tests using health-checks in docker-compose and tried this approach and it worked after some trial and error. An alternative would just be having my backend retry the connection to rabbitmq until it succeeded (within reasonable boundaries).

Since the communication with redis and rabbitmq now happens via the containers I had to change some code as well by incorporating environment variables so that it will pick up that the code now runs in a container. With this setup I can still run it normally without containers as well, if that is wanted. 

Lastly, I updated my GitHub Actions workflow for tests to include containers for Redis and RabbitMQ, and my end-to-end tests now run successfully. 




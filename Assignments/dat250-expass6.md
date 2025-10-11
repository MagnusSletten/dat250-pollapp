# Assignment 6: Event handling with message brokers

For this assignment I incorporated a message broker following the amqp protocol, my choice was RabbitMQ. 

The purpose of a message broker is to allow low coupled forms of communication between systems which is easy to extend. 

The flow now with votes is that votes hit the backend at which point a VoteEvent is sent out to any listeners via AMQP, the consumers of these events can do various things with them. Right now, the PollApp itself listens for the events within VoteController and does business logic and persistence when receiving them. Extending this with another listener which for instance could do analysis of incoming votes would be trivial as it would just be registering a new listener to the events. 

## Problems encountered
I based my message broker code on the rabbitmq tutorial, so my starting point was their Topic-Broker and receiver, from here I didn't encounter major problems. I did choose to play around a bit with initialization after getting a working version which took a bit of time. I tried getting my PollBroker class to work as a Bean so it's handled by Springboot and this works now, but it took a bit of trial and error. 

For deployment I decided to use Docker, and I did not have any problems with it. 

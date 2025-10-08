# Assignment 6: Event handling with message brokers

For this assignment I incorporated a message broker following the amqp protocol, my choice was RabbitMQ. 

The purpose of message broker is to allow low coupled form of communcation between systems that's very easy to extend. 

The flow now with votes is that votes hit the backend at which point a VoteEvent is sent out to any listeners via AMQP, the consumers of these events can do various things with them. Right now the PollApp itself listens for the events within VoteController and does businuiss logic and persistence when recieving them. Extending this with another listener which for instance could do analsis of incoming votes would be trivial as it would just be registering a new listener to the events. 

## Problems encountered
I based my message-broker code on the rabbitmq tutorial, so my starting point was their Topic-Broker and reciever, from here I didn't encounter major problems. 
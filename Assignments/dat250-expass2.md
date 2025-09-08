# Report on setting up Polling API

What has been done:

Set up basic java classes representing the `model` part of the design, these objects can be made and tested independently from springboot setups. 
Then springboot specific classes that use these model objects were made such as PollController, UserController and Votecontroller. The controllers annotated with `@RestController` can interact with HTTP requests and handle JSON deserialisation/serialisation via `Jackson`. I was impressed with how easy it was getting to a working API using this functionality. It's straight forward to handle which parts of Java objects should be included in JSON.

We were also advised to use lombok which turns out to be great advice because it made setting up all the basic data classes really simple. 

Overall, this task went pretty smoothly where the biggest problem I had was jumping into implementation too quickly before properly reading the task, this left me with a functional multi-question poll with a working React front-end, the latter took some time to iron out all the minor React/JavaScript-problems (stable object IDs..etc..). Then it took more time to backtrack to the much simpler one question poll. 


## Issues

Initially I had some problems with setup which were fixed by setting classpath variable in `gradlew.bat`, after this was done, I didn't have direct technical problems.

## Further improvements

1. Expand testing suite.

    Right now there's some basic model-level unit tests as well as a few integration tests using a suggested RestClient setup, this is okay but coverage is too small. Ideally different components should be mocked to make debugging easier. 

2. Improve error handling:
    
    The errors should propagate up to controllers that should send clear errors to the user, right now the design is slightly amateurish, but I will improve on this. 
 
3. Github actions:
I have set up automatic testing via github actions, but I think this can be improved in-terms of speed by making a docker image for this which would lower setup time for gradle, caching is an alternative. 

4. Simplify Java backend classes: I initially made a design which felt natural where the Poll class only contains a reference to its creator via their UserID, but looking at lecture notes I see a better solution here with direct Java object references here. This code can currently be cleaned up a lot with a design like this. 

Code is available [here](https://github.com/MagnusSletten/Springboot_PollApp)

# Update on issues
### Important note: Everything below has been written after the due date for this assignment. 

Being inspired by the lectures I implemented a new model/controller design where the Java objects: User, Poll, Vote... has real references to the java objects that relate to them as opposed to just ID references. To make POST process a bit simpler I made small DTO classes which could easily be transformed into the real Java objects with the backend information that wouldn't be easily accessible from the frontend. (Without further calls). Then I also used the jackson json annotations, for instance @JsonIdentityReference(alwaysAsId = true) to avoid returning too much nested object details for GET http requests. 

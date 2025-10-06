# Assignment 5: Redis cache implementation

For this assignment we introduced a cache to the polling app which speeds up fetching vote counts. The purpose of it is to avoid queries which are not optimal for data that are frequently needed.

## Set up
I didn't have any major problems with Redis and opted to go for a docker container setup as this is easy to set up and relevant for later assignments. It would be nice to upgrade this with a full docker compose later. 

## Problems encountered
For both Use-Case 1 and 2 referenced in the report I encountered no problems using cli commands for instance with: `hset` to create hashsets for Redis. 

Integrating the cache into my Poll app which already included a JPA persistence setup with the H2 database definitely took a bit of work, but less so than expected. I made a Cache class: `...\Model\Poll\Vote\VoteCache.java` which I could then instantiate in my PollManager class which already included the repositories handling persistence. The logic for caching was straight forward and not problematic. I set an expiration time for poll-votes to be 20 minutes. I also worked a bit more with improving my persistence classes and got most tests to pass. Due some tweaks to code my frontend also had to get a small update. 

I did not write direct tests for the cache but did some manual testing to see that behaviour matched expectations. By setting expiration timer to 10 seconds, I could check that Poll-votes where invalidated as expected. 

As always, it could be done in a more rigorous way and a refactor would absolutely be in order before it went anywhere close to production. 

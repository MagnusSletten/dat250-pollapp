# Experiment Assignment 3: front end development

For this assignment I built a basic frontend using react/vite which interacts with my backend API created in assignment 2.
Within the frontend I created simple typescript classes to build Poll objects with functionality to return JSON corresponding to the DTOs(data transfer objects) I created in my backend. This makes the poll contruction more standardized and opens up for simple JSON schema validation, then I had a seperate react component `PollView` which takes the poll information and displays it in a relatively nice way. 

Aside from the components mentioned in this task I also added a small component to allow users to log-in, this is currently more of a placeholder and it doesn't directly interact with the backend but it changes the userName within the poll. The three components were used within App.tsx where a tabview was created using a simple button layout. 

I also went back to the backend to finish up the tests there.

## Problems encountered:
For this assignment I did not encounter any major problems, there's is however not a single part of this codebase that currently could not be improved by a large margin, but getting to that production ready application takes more time. 

Some problems that were solved:
 1. Persistent keys for frontend objects, classic react problem which has many solutions. I added ID-fields within model objects that are excluded from JSON.
 2. React state updates using poll-model objects. In order for a react state variable to trigger rerenders the setter method needs to use a *new* object and not an object which is just modified. To get new object references when for instance adding an option to the model then it's not enough to simply mutate a dictionary style object, instead the old object is copied with appended information for useState to work as expected. This is normally not a problem at all, just basic react design, but I when introducing my model objects I had to be careful here.

 ## Some simple improvements that can be made: 

1. Add env files to simplify deployment with for instance the URLs involved to backend
2. Improve what information user recieves upon errors, also related to the backend by wrapping the responses properly. They should return proper error codes as well.


 





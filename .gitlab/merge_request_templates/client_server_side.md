Closes <!--- specify issue number here preceded with "#" symbol -->

# Checklist
Particular:
- [ ] Have you tested your front-end code in all possible manners?
  - Manually by starting the application - does different actions produce the desired outcome
    - Send requests to the server
    - Redirect the client to desired scenes
    - Follow the common aesthetic of the other scenes created so far
- [ ] Have you tested your back-end code in all possible manners?
  - Postman - does the back-end endpoints and functionality works are desired
  - Using the *h2 database* console - reached on http://localhost:8080/h2-console in case the server is started on port `8080`
- [ ] Have you tested the functionality of the implemented feature in general?
  - Using manual testing from the application, what happens on the server side? 
  - Is the request recognized right, is the corresponding response right? 
  - Is the desired behavior achieved?

General:
- [ ] Do you follow all style conventions agreed on in the "Code Of Conduct" and the "Checkstyle"?
  - Classes, fields and variables naming conventions
  - Sufficient Javadoc explaining the achieved functionality
  - Sufficient test coverage - all possible outcomes from a back-end endpoint should be tested

- [ ] Make sure you merge into branch `development`


# Details
<!---
Elaborate on the task you had to implement, the desired behavior and the one you achieved. 
If something made you struggle, make sure you include the reason and the information sources that lead to the particular approach in here, so that the reviewers can easily get familiar.
-->

# How to test
<!---
Give a detailed summary of how your piece of code can be tested.
Include pictures showing the process, covering the desired behavior to be achieved.
State explicitly once more the expected behavior of particular occasions and guide the reviewers on how to reach them.
-->

[//]: <> (
Information should be inserted instead of the comments of the format "<!--- -->". 
If not removed, the comments would still preserve the information containing - for example, commented tasks are still counted by GitLAb, so make sure you delete them.
)

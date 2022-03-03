# Agenda of Meeting-4

Location: Drebbelweg, PC1, Cubicle 8

Date: February 29nd, Tuesday

Time: 14.45

## List of individuals

Chairperson: Monica Paun

Notetakers: Julius Gvozdiovas, Kaloyan Fachikov

Attendees: Alexandra Darie, Kayra Bahadir, Marijn Luime, Florena Buse

## Preface

- Improve the HCI report based on the feedback.
- Create the second sprint and assign the issues.
- Read about the BuddyCheck assignment on the Brightspace and write down any questions.

## Meeting Schedule

### [14:45 - 14:50] Check-in

- Is everyone present?

### [14:50 - 15:00] Summary of last week

- How are things going? What did everyone do in the previous week? Did they manage to complete their tasks?
- What is left in progress?
- How do you feel about the coding part now that you have started?

### [15:00 - 15:15] Feedback from the TA

- Coding feedback 
- Merge Request and Gitinspector feedback
- New version of the HCI report feedback

### [15:15 - 15:25] Action points for next week

- Finish the final version of the **HCI report**.
- Create **JavaDoc** for the code implemented last week.
- Prepare the questions for the game and upload them in the 'activity-bank' repository on GitLab.
- Resolve the issues assigned in the second sprint.
  - 'Multiplayer Gameplay' button
  - Add CSS stylesheet
  - Visualize question
  - Queue (Waiting Room) 
  - Power-up cards available 
  - Missing username error
- Start working on the individual **assignment 4B** - giving and receiving feedback. The deadline for this assignment is in week 3.5.

### [15:25 - 15:35] Questions to the TA

- Everyone has the opportunity to ask out supervisor (the TA) for suggestions about points we haven’t mentioned during the meeting yet, or things that are still unclear.
- Ask for overall feedback. How we are doing? Any advice to improve?

### [15:35 - 15:45] Tip & Top

- Everyone has around 2 minutes to give personal **constructive** advice, or praise, as well as to give some general suggestions about the team workflow and comment on what they find great within the current environment.

### [15:45] Closing

- Choose the next chairperson and notetakers.

# Notes Fourth Meeting
# Agenda items
## Summary
- How are things going? What did everyone do in the previous week? Did they manage to complete their tasks?
	- Notes and agenda of the last week
		- Marijn and Alexandra merged the notes into the agenda
    - Implemented home, help, and preparation for solo game screens
        - Kayra implemented the home screen and the help screen
            - Styles using CSS (merge request pending)
        - Kaloyan -> preparation screen
    - Endpoints GET -> User in lobby, questions
        - Kaloyan -> POST request to store SingleUser
    - Endpoint POST -> Username input for multiplayer game
        - Monica implemented this, using MultiUser class + repo
    - Endpoint DELETE
        - Marijn is still figuring it out
    - HCI report draft
        - Changed some of the things in the feedback
        - Monica contributed to the HCI report
        - Marijn helped with HCI
        - Alexandra worked on the HCI
- What is left in progress?
    - Marijn -> deleting a player as endpoint

## Feedback from TA
### General tips
- If personal situations occur which makes it difficult to work, come to TA or academic counselor. Also, nobody expects you to contribute as much during the week before exams/during exams.
- Work more continuously — more merge requests and commits (**everyone should have a merge request each week**).
    - Currently, Gitinspector is flawed and has mistakes
    - Marijn and Alexandra seem to have no lines contributed
        - Marijn didn’t finish merge request by the end of the week
        - Alexandra’s lines were overridden by Monica
- Should try to contribute more equally.

### Code
- Do not need to focus so much on testing. Unit tests for each class — 1 - 2 per method are enough. No need to use “mocking” or “integration testing”...
- Don’t think about coverage — line or code.
    - Line coverage — each line is tested.
    - Branch coverage — usually used in “switch” and “if-else” cases, when there are several paths the execution can go through.
- Have more GitLab activity — comment and review code in merge requests.
- Do not fix others’ code if it’s about to be merged. Better let them know what your concern is. If the code is already merged, discuss it with the group — make sure everyone agrees with the upcoming change.
- Be consistent in code style.
    - Make sure the endpoints return similar objects — either an `Object` or a `ResponseEntity`.
    - Consistency among `Request` parameters.

### HCI
- We should improve the prototype and include it in the HCI report. Could do “Before” and “After” comparison. A statement about what the change is.
    - The final prototype must be improved based on the feedback, and such improvement documented.
    - Can include a link to the prototype, but we must include pictures of the prototype.
    - The design of our game does not have to match the final design in the HCI report.
- Strive for 6 citations at the end of your HCI report — not a hard requirement, but for the sake of the grade.
- The **reviewers** have gone several times over the prototype following several scenarios.
    - Describe the scenarios.
    - Mention how many reviewers came upon a particular issue.
    - Give context on the experts: how many of them were there, what is their expertise.
- Describe how would you prioritize the issues and the solution for them.
    - In the improvements section, include a list of issues and a list of improvements prioritized.
    - Each issue should have a parallel improvement — clearly indicating which improvement corresponds to which issue.
    - The improvement section is quite important — describe explicitly what the change is.

### Additional advice
- Keep a list of all agreements taken among the team members.
- Do not overwork yourself. If someone works more this week, feel free to contribute more the next week (to compensate).
- Sprints are useful as the planning part. Plan the workload for the next week in advance. It is allowed to update the sprint by both adding issues (if new arise), or removing some from the sprint.
- Add a “Review” label to GitLab, for issues which have a merge request open.
- We should have a list of endpoints that we want to implement 
- The entire class structure
- Consistency for what we return at the backend 
- BuddyCheck is **very** important (hard deadline) 
	- Make sure to be honest 
	- For some questions, only lecturers will be able to see the answer
	- Some responses (anonymized) will be shown to others 
	- Raise any problems we have in the project (as it is the midpoint)

## Questions to the TA
Kaloyan -> Can we start working on it right now? Or does the system only open next week?
 - Florena isn’t sure, it’s possible that now it can’t even save progress.
Monica -> What should we do not to have such a difference in the number of lines we have?
 -  Try to pick features equally. 
 -  Try to get inspiration from others. 
 -  A trick — you can always test a little bit more. If you did one issue yourself,  and Kayra did a bigger one, just pick up another issue. 
 -  Don’t overwork yourself. Don’t forget that you should know both frontend and backend. 
 -  There will be “surprises” with this...

Julius -> How do sprints work exactly?
 - Try to see which ones you can do? Do planning each week. 
 - You can add new issues to the sprint, or remove them. As long as we follow the flow, it is fine.

Julius -> How much should everyone contribute?
 - Contribute to everything equally.

Alexandra -> Know how much code the others wrote? Maybe we assign, by mistake, differently “hard” issues to different people.
**Response:** We can ask Florena to get the report of the Gitinspector.

## Action points for next week
- Finish the final version of the HCI report
    - Send the report by Thursday night to Florena
     - Kaloyan -> Initial part
        - Initial prototype
        - Final prototype
        - Keep Florena’s remarks in mind
    - Kayra -> Formatting
        - Read it, paraphrase, structure it
        - Find references
    - Monica -> Mention issues
        - Issues that each expert found, in each scenario
        - At the end/beginning of the results section
    - Julius + Marijn -> Improvements part
        - Image before and after
        - Explanation of the issue that we are solving
        - Point 8 from Florena’s feedback
        - Point 9 from Florena’s feedback
        - Point 10 from Florena’s feedback
- Coding prep
    - JavaDoc
		- Florena can ask how much JavaDoc do they expect
        - Alexandra - add JavaDoc to existing code
    - Testing - test coverage
        - Monica and Kayra will talk
    - Code consistency
        - Julius -> Return response entity
    - Initialize the database with mockup data
        - Kaloyan + Kayra -> Research how to initialize the database with data
- Activity bank (create JSON)
	- We must prepare the questions for the game and upload the to the `activity-bank` repository on GitLab
    - Everyone -> Comes up with 4 activities in the specified JSON format
        - We will create a google doc, everyone will upload it to that
    - Monica -> Merge request in the `activity-bank` repository 
- Resolve the issues assigned in the second sprint.
    - Before we can do this, we decided to determine and decide on the structure of the entire project.
		- Decide on a structure for the back-end — classes, hierarchy, relations, endpoints, etc.
- Start working on the individual **Assignment 4B** - giving and receiving feedback. The deadline for this assignment is in week 3.5.
- Teamwork for BuddyQuest

## Tip & Top
Kayra 
- top to Julius — Has more experience with the general environment, brings more structure that will be more like careers. Created a tutorial on the “checkstyle”. Good influence on the group.

Monica
- top for Kayra and Kaloyan — Did their part - They are involved.
- top for Kayra — Good suggestion on classes
- tip to Julius — Be more involved in the HCI report

Kaloyan 
- tip to Marijn — Do not create so many merge requests, then make them drafts, and close them after.
- top to Marijn — Enjoy how you answered comments in GitLab.
- tip to Alexandra — I see you approve many merge requests and some questions appear — “Did she check the code, or just approve it?”. So, please comment once you review a code, and always review a merge request before approving it (the last is for everyone in the group).

 Julius
- top to Kaloyan — Thank you for going the extra mile.

Alexandra
- top to Kayra and Kaloyan — We should all appreciate Kayra and Kaloyan’s “intro”.
- top to Julius and Kayra — Thanks for the help!

Marijn
- top to everyone — Everyone worked great on the project.

## Closing
- Choose the next chairperson and notetakers.
    - Chairperson — Alexandra
    - Notetakers — Kayra, Monica

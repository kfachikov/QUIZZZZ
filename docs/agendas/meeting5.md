# Agenda of Meeting-5

Location: Drebbelweg, PC1, Cubicle 8

Date: March 15th, Tuesday

Time: 13.45

## List of individuals

Chairperson: Alexandra Darie

Notetakers: Marijn Luime, Monica Păun

Attendees: Kaloyan Fachikov, Florena Buse, Julius Gvozdiovas, Kayra Bahadir

## Preface

- Does anyone have any suggestions regarding the current sprint?

## Meeting Schedule

### [13:45 - 13:50] Check-in

- Is everyone present?

### [13:50 - 14:00] Summary of last week

- How are things going? What did everyone do in the previous week? Did they manage to complete their tasks?
- What is left in progress?

### [14:00 - 14:15] Feedback from the TA

- Coding feedback
- Merge Request and Gitinspector feedback

### [14:15 - 14:25] Action points for next week

- Decide upon answers for the following questions: How server and client communicate? How the question instances are sent and what do they actually consist of?
- Update the code of conduct with the new rules.
- Decide upon new deadlines.
- Finish the unresolved threads and empty merge requests from the previous sprint.
- Resolve the issues assigned in the fourth sprint.
    - Intermediate "Answer-revealing" screen
    - Visualize score counter
    - "Congratulations" screen

### [14:25 - 14:35] Questions to the TA

- Everyone has the opportunity to ask out supervisor (the TA) for suggestions about points we haven’t mentioned during the meeting yet, or things that are still unclear.
- Ask for overall feedback. How we are doing? Any advice to improve?

### [14:35 - 14:45] Tip & Top

- Everyone has around 2 minutes to give personal **constructive** advice, or praise, as well as to give some general suggestions about the team workflow and comment on what they find great within the current environment.

### [14:45] Closing

- Choose the next chairperson and notetakers.

# Notes Fifth Meeting
# Agenda items
## Check-in
Approx. 13:55
Kayra attended online through Discord with audio and video, the others attended physically and everyone was on time.

## Summary of last week
Approx. 13:55
- How are things going? What did everyone do in the previous week? Did they manage to complete their tasks?
  - Notes and agenda of the last week
    - Julius and Kaloyan merged the notes into the agenda meeting4
- Kaloyan: worked on server, queue, error handler with setVisible textFields, created the fourth sprint and included issues
- Julius: worked on queue, fully implemented it. Added a transition between the queue and Multiplayer question, created mockup multiplayer scene
- Marijn: implementation for error handler missing username, transition between questions, leaderboard
- Monica: create the 4 different classes that inherit from the AbstractQuestion class, contribute to the tests for one of Alexandra’s merge requests
- Alexandra: implementation to visualize questions for solo and multiplayer, gameclasses and commons, different types of questions, response controller that sends answers to answer repository, in which solo and multiplayer are separated
- Kayra: implementation to receive activities, parse activities, administrator panel where admin can input json files with activities

- What is left in progress?
  - Marijn -> Transition between questions, leaderboard
  - The group as a whole -> Add Javadoc so that ./gradlew javadoc gives 0 warnings

## Feedback from the TA
Approx. 14:05
- Coding feedback:  
  - Add in the agenda having a demo of the project in the TA meeting.
- Merge Request and Gitinspector feedback:
  - Let people who worked less pick up more extensive, longer issues .
  - We could do pair programming.
  - Agree on a procedure regarding the design choices.
  - Don’t prioritize the refactoring.
  - Yellow flag: the time estimated is not used by everyone (use it for the issues); some people are not spending the equal amount of work on issues; the gap regarding the code is to big; the title of the commits; to many files for commits
  - Red flag: code review needs to be more balanced ( we can agree on something from the beginning, create some rules) - avoid lengthy merge request

## Questions to and from the TA

- Florena: Does anyone need more help?
  - Julius: we were discussing on discord how to implement things like the roundTimer, I could help Alexandra with the roundTimer

- Florena: Do you have problems with design choices, if so, how can you approach the design choices better?
  - Kaloyan: on Discord we have more deep discussions on design choices, but that takes a lot of time, we can discuss better in the informal meeting after the TA meeting

- Florena: Maybe you can discuss before implementing the issue, not after or the people handling the issues can decide the design choice for the specific issue not the other people
  - Julius: Back when I implemented activities I thought we needed only 1 per question but Monica experienced something else
  - Monica: we need 3 activities for questions, so I needed the activity from server to common and I used a dependency, but that is not allowed
  - Julius: some decisions early in the project are bad in current knowledge, we also need to refractor some things
- Florena: Don't prioritize refactoring
  - Julius: there are some errors with names overlapping in the project, because the architecture of the project is different than we thought of initially, so maybe it is not really
    refactoring, we also need to discuss some things in the project

- Florena: how about the code of conduct, did you think about it?
  - Alexandra: we haven't talked about that yet, we can talk about it in the informal meeting
  - Julius: the conduct never came up again, should we edit it in our discord
- Sander: that indicates that the project goes well, because then you don't need the code of conduct, you only update the code of conduct when necessary, we look at the
  progress of the code of conduct, make sure the latest version is on gitlab, we can not look on your discord
- Florena: you should keep the code of conduct up to date to be able to handle conflicts better


- Florena: should we set up an internal deadline for must haves? you should prioritize the must haves, you should follow the official backlog
  - Kaloyan: the fourth sprint finishes the must haves
  - Alexandra: I don't know if we finish the must haves this week, because there could be unexpected issues, if not this week we will finish the must haves next week
  - Julius: it is hard to work simultaneously on the same file, we have had to wait on each others must haves to be able to complete our own must haves

- Sander; the aim of this project is not a perfect product (40%), just if the teamwork process (40%) went well

## Action points for next week
Approx. 14:40
- Decide upon answers for the following questions: How server and client communicate? How are the question instances sent and what do they actually consist of?
- Update the code of conduct with the new rules.
- Decide upon new deadlines. We should decide on an internal deadline for the musts.
  Deadline:
  - Merge requests done Friday night before sleep.
  - The code review is done during the entire week.

- Finish the unresolved threads and empty merge requests from the previous sprint.
- Resolve the issues assigned in the fourth sprint.

###THE FOURTH SPRINT

####Alexandra
- Refactor Commons classes
- Integrate the SoloGame
- Add missing JavaDoc

####Julius
- Update the current Checkstyle configuration
- Visualize score counter
- Create the transition screen
- Add missing JavaDoc

####Kaloyan
- Create merge requests templates
- Update Code of Conduct
- Visualize score counter
- Create the transition screen
- Add missing JavaDoc

####Kayra
- Refactor Commons classes
- Integrate the SoloGame
- Send JSON files to server (Admin Panel)
- Add missing JavaDoc

####Marijn
- Create 'Congratulation' screen for the SoloGame
- Create all the missing scenes
- Insert the leaderboard in the SoloGame prep screen
- Add missing JavaDoc

####Monica
- Refactor Commons classes
- Integrate the SoloGame
- Add missing JavaDoc

## Tip & Top

Kaloyan
- top: I really enjoyed the discussion between Julius, Alexandra and Monica with a lot of positive, constructive comments and everyone having the same goal that caused me to
  feel a teamspirit.

Kayra

- tip: to Monica and Marijn, maybe if you could make more in depth reviews so the merge
  requests get more and better feedback.
- tip: sometimes the discussions are tense, but if you understand that the other person
  wants the best for us
- top: the discussion the day before the TA meeting was very positive, which was a good
  improvement.
- top: everyone worked well, which I liked.

Alexandra

- top: I like the workflow when I work with Monica, we communicate a lot when we work	together.
- top: to Kayra, I liked it when you helped me with the round timer and thank you for all	the advice you gave me.
- top: to Marijn, I like that he started working more on the project.
- top: to Julius and Kaloyan, we are finally on the same side and we understand each other.

Monica

- top: there was better communication in the group
- top: to Alexandra, I liked working with Alexandra.
- top: to Marijn, I feel like Marijn became more confident in the project.

Julius

- tip: to Kayra, make sure you don't overwork yourself by helping everyone, you don't have to respond immediately, especially when you are sick
- top: to Alexandra, Monica, Kayra thanks for stepping up and doing additional work.
- top: to Marijn, good that you worked more on the project.
- top: to Kaloyan, I appreciate you for all the work you have done.

Marijn

- top: the entire group had good communication.
- top: the group as whole has improved the resolving of conflicts.
- top: to Kayra and Kaloyan, thank you for all your help, it really helped me.

## Closing
- Choose the next chairperson and notetakers.
  - Chairperson — Marijn
  - Notetakers — Kayra and Alexandra





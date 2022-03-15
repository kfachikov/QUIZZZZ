# Agenda of Meeting-5

Location: Drebbelweg, PC1, Cubicle 8

Date: March 15th, Tuesday

Time: 13.45

## List of individuals

Chairperson: Alexandra Darie

Notetakers: Kaloyan Fachikov, Monica Păun

Attendees: Marijn Luime, Florena Buse, Julius Gvozdiovas, Kayra Bahadir

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

# Notes Fourth Meeting
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
- Julius has a lot of code
- Marijn and Alexandra are a bit behind on code
- Monica, Kayra have a good amount of code
- Kaloyan has a lot of code
- People with less code should choose first and more extensive issues
- Kaloyan helped Marijn
- Pair programming could be helpful
- Coding feedback: to add in the agenda showing a demo of the project in the TA meeting

- Yellow flags

- Florena: not everyone uses time estimating for tasks
  - Julius: time estimates on issues or merge requests?
- Florena: both are good
  - Kaloyan: should we all do time estimates on either one or on both?
- Sander: I recommend assigning time estimates on issues, you don't have to change past things

- Florena: there is still not an equal amount of time spent on the project
  for Marijn the gap is still too big, the group should use the previous decided method to assign tasks, with extensive issues and decide how to divide them
- Sander: the gitinspector is still an indicator tool, we look more in depth after the gitinspector

- Florena: I don't like the titles of commits and some commits are too extensive, more focused/smaller commits, more clear titles on commits
  - Julius: some commits span a lot of files, because some things affect a lot of files, so I have to edit a lot of files, should I commit when it doesn't compile?
- Florena: you should only commit when it compiles, but try to do more smaller commits

- Florena: code reviewing is not satisfactory, there is an unbalance in code reviewing for Monica and Marijn not enough, they should do more code review
  - Alexandra: some merge requests are very large and take a lot of time to review or some merge requests have differing views on things, so we should set up rules for merge requests, like when you can approve them, so there should be more consistency
- Florena: maybe you should make the issues smaller
- Sander: some merge requests have less code review comments in comparison with others, so try to find a balance, keep merge requests smaller so code review is easier
  - Julius: for typos or statements missing, can I fix it myself?
- Sander: you can also add recommended changes in gitlab, do not fix it yourself
  - Julius: is it fine to do larger merge requests if you can not split it
- Sander: it is not bad, but it makes it harder to code review, so try to make the merge requests smaller

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
- Decide upon answers for the following questions: How server and client communicate? How the question instances are sent and what do they actually consist of?
- Update the code of conduct with the new rules
- Decide upon new deadlines, decide for an internal deadline for the must haves
- Finish unresolved threads and merge requests from the previous sprint
- Finish the issues of the 4th sprint

Informal meeting

- Alexandra: sprint and deadline, finish thursday evening, code review friday evening, save weekend for major changes or unexpected issues
- Julius: at least 2 issues per person, Kaloyan and jJulius less, deadline wednesday for first implementations
- Kayra: saturday 12:00 deadline, code review, sunday merging 3pm
- Kaloyan: people see things differently, so an agreement today would be nice, the person who is implementing an issue can say he/she has achieved completion by friday and code
  review saturday
- Julius: I had a discussion with monica about a merge request because I thought it was not finished yet, but I was confused about when it was finished because there is nowhere
  a definition of finished, also a lot of labels are wrong
- Alexandra: I think the issues need to be more explicit
- Kaloyan: so deadline friday before you go to sleep
  - Deadline: merge requests done friday night before sleep, code review saturday and sunday
- Kayra: everyone should code review everything
- Kaloyan: I don't agree with that, that would be a lot of work
- Julius: I would like people code reviewing merge requests even before they are ready, so they work well before the deadline ended, maybe 2-3 merge requests per person per day
- Alexandra: what are the requirements for a merge request to be approved, just that it doesn't break the project or that it is perfectly implemented, what can be rules for
  merge requests for it to be approved
- Kayra: there are 2 ways of code reviewing, about crucial things or aobut more personal thoughts about things when people don't agree about something
- Julius: some merge requests can not be approved when it breaks, but it can be approved when there are small things you don't agree with
- Kaloyan: some threads arise because we don't agree about some things
- Julius: maybe we can agree on a thread template to indicate its severity
  - Issue problem: bad tasks
    - we should delete all the issues and recreate them, create tasks for them on the fly
      add tasks for the issues when you are working on them
- Julius: meeting on saturday: sprint planning, assign issues, add tasks, sprint ends on saturday, merge development to main on saturday
- Kaloyan: for code of conduct, if you don't work consistent, don't expect people to code review your code all weekend, for thread severity choose major/minor problem or suggestion
- Marijn: look at all unresolved threads before approving and also comment on them if you disagree with them and you think it can be merged

## Tip & Top

Kaloyan

- top: I really enjoyed the discussion between Julius, Alexandra and Monica with a lot of positive, constructive comments and everyone having the same goal that caused me to
  feel a teamspirit.

Kayra

- tip: to Monica and Marijn, maybe if you could make more indepth reviews so the merge
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
- top: to Julius and Kaloyan, we are finally on the same side nad we understand eachother.

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



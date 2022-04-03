# Code of Conduct

## Assignment description:
### In your own words, describe what you need to do as a group in this course.

- Develop a **professional** relationship between each-other in order to create a productive environment and amp-up results. Including the establishment of a fair work ethic and division.

- Have strong connections and communications **beyond the project** and have support each other.

- Build a polished quiz game about energy awareness. In the game, the player is given a question about energy usage of various items, and must answer with the given time frame. It should have both a single-player mode (where users can practice and test their knowledge), and a multiplayer mode (where users can compete with others). This will be done using JavaFX for the client side, and Spring for the server side.

## Target or ambition level: 
### What grade are you working for?

- Every member of our team has a goal of getting a 10. However, the common opinion is that a grade around 8-9 is more realistic and would satisfy most of the group members. It is preferred to stick to Grade 9 as a lower-bound.

- The finished product of the project, the game, must be polished, without bugs. Even if the grade does not fully satisfy us, we must be content with the finished product.

## Products:
### What should you deliver at the end?

- A working application that fulfills the requirements of the backlog.

- With the application, there must be tests for the application, and the application must pass the tests.

- Not just the file application, but the entire GitLab repository. All branch names, merge requests, issue names, small aspects – the “process” along the creation of the project. This includes:
  - GitLab issues, for the backlog of the project. These should be according to the backlog rubric of the OOP Project.
  - Agendas of TA meetings, inside /docs/agendas/ folder, in Markdown format
  - Notes of TA meetings, inside of the same file as the agenda for the meeting
  - Commits, comments and merge requests on GitLab. These should be nicely formatted, with clear and consistent language

### On which platform do you share which documents (Discord / Miro / MS Teams)?

- We use Discord to share documents.

### What standards must the work submitted meet?

Contribution to GitLab:
- Issues and merge requests follow the templates
- Commits only affect a couple of files, commit messages give a clear summary of the change
  - Developers regularly *push* to the remote branch, so their work can easily be followed by the other team members.
  - A single *push* could contain several commits if they all address the same "atomic" feature developed. The attention is on regular commits - defining particular code snippets, and regular contributions to the remote repository so the other team members are kept up to date.
- When developing, a separate branch is created to isolate the new feature, which is later merged into the main branch using a merge request
- Merge requests do not stay open for prolonged periods of time
- Team members not only contribute, but also do code review
  - Code reviews are done regularly, as commits and *push*-es
  - Code reviews should be objective. People could regularly end up having different opinions and ideas, but it could be the case when both approaches work and accomplish a functional piece of code that could be used in the long term. Thus, reviews and threads should be marked as either "suggestion" or "problems". The former should suggest an approach that can only improve the efficiency of the cod. The latter should regard possible problems that could occur. In their description, the situation, that could cause this problem should be explicitly stated - could be either a client desire, a server bug, or simply a plan for future integration of that particular feature.
- Team members make use of the *Time tracking* feature of the **issues** created.
- Each Merge Request should have at least 3 people who have commented on them before being approved.
- Each team member must clearly list **all** the changes that they have made in their merge request.
- The threads for a merge request should be closed only by the person who opened them. The reviewer should check constantly if the author of the request responded to their comments.
- We can only approve a merge request with open threads:
  - If we add a comment explaining why we approve the merge request despite having open threads.
  - If we look over all the threads that are not resolve at that time.
  
- A team member is allowed to merge other's requests, if that would prevent work being stalled.

Code Style:
- 4 spaces
- Camelcase variables (e.g. firstName)
- No single letters and shortens for name of variables
Java Doc
- <resolved issue number>-<name1>-[name2]-... (e.g. 4-draft-backlog). Strive for at most 3 words.
- Try to commit small parts of code - regular commits.
- Do not create create static methods or fields that are not final.


## Planning:
### How do you ensure that each group finishes everything on time?

- Every singleUser gets prepared for the check-in by noting down 2 sentences of what they did, so that the opening consumes as little time as possible.

- Set inner group deadlines. Have the “tutor” (the TA in our case) to check briefly on how we did the things and only submit then.
  - Merge requests should be staged as ready by Friday 23:59 each week. A sort of soft deadline, allowing people working at night to finish their contribution even if that means they should work overtime. The goal is to have all the merge requests ready when everyone wakes up on Saturday.
  - *Clarification*: If someone leaves their tasks for the last moment, that person should be ready for some major problems to occur when it comes to others’ opinions. Thus, they should be ready to spend some time giving insight into their code to others. However, if they *commit* and *push* regularly, the other has the responsibility to review their code regularly so problems are found before they become unsolvable. In other words, it's developers' responsibility to allow others to be familiar with their workflow, while it's the reviewers' responsibility to keep up to date with the code themselves.
- Each meeting, any action points that can be divided into smaller tasks for individual people, or for smaller groups of people, will be assigned (either by voluntary basis, or, if that fails, randomly) that task.

- For such smaller tasks, a “soft” deadline will be negotiated, taking into account that time should be left for others to review the completed task before the “hard” deadline approaches.

- Each Merge Request should have both a clear "soft" and "hard" deadline. Every team member should specify the exact time of the day for this deadlines.
  - "Soft" deadline means the finishing time that a team member is trying to achieve.
  - "Hard" deadline represents the time until a team member fix the issues encountered and works harder.

- If one of the members find themselves struggling with their issue or with understanding the suggestions made by others on the merge request, they should ask for help.
  - Asking for help will allow others to be kept updated with the progress of that merge request and will make them understand the lack of commits.

### Did you clarify who will have a final say in the final deliverable and submit it to Brightspace on behalf of the project group?

Chairperson:
- Responsible for any deliverables.
- Creates agenda.
  - Asks for suggestions for agenda items.
- Uploads the files into Brightspace / Gitlab.

Notetakers:
- Merges the 2 note files and commits on the bottom of the agenda.

## Behavior:
### How do you treat each other in the group?

- Every member of the team should treat the others with respect.

- When a problem occur the members of the team have the responsibility to dive into what really happened that caused the problem and let the others present their side of the story. Blaming each other is not a solution.

### How do you handle disagreements within your group?

- The disagreements should be handled with communication, everyone has the right to express their opinion.

- If disagreement on implementation arises, the decision-making process (as described in Code of Conduct below, in the Decision-making section) will be used to make the final decision.

- When personal or emotionally loaded disagreements between two team members occur, other team members intervene and allow the conflict to “cool down”, before continuing.

### Could your guide or student assistant be involved in reaching consent?

- The student assistant should not be involved in our conflicts, but if we cannot come at an agreement we will discuss it with her.

- Should a complete divide appear in the group, which does not allow us to progress, our TA will be consulted as the final tie-breaker (even if they end up flipping a coin).

### What do you do if someone is late or is absent during a group meeting?

- If someone is late to a meeting, they will have to bring sweet snacks to the next meeting.

- If someone is late consistently, then that would be discussed among the team members and at the end, eventually, will be mentioned to the TA.

- If a person is absent from a meeting and did not give any notice in advance, the meeting should proceed as intended, so that the workflow and the decision making process are not stalled. The one being absent has the responsibility to get informed about the decision taken.

## Communication: 
### In what ways do you communicate with each other as a group and among yourselves? (in the studio/MS Teams/Miro/Discourse)

- Discord, WhatsApp, and Mattermost are the applications we use for communication.

## Commitment:
### How do you determine the quality of each group's work, so that each group delivers the same quality?

- We determine the quality of each-other's work by the quality of their code, which can be seen in the number of warnings in their code and the style of their code.

### How do you measure the commitment of the chairs and minute takers?

- We measure the commitment of the chairs by the quality of their agenda’s and their quality of leadership during the meetings. People during the meeting should all have an opportunity to raise questions or concerns, and interruptions should be kept to a minimum by the chairperson.

- An agenda is considered to be of acceptable quality if it:
  - Contains all mandatory items, if specified by the TA or any assignments.
  - Has consistent styling, and does not significantly differ from previous agenda formats.
  - Reasonable amount of time is devoted to each item.
  - The agenda does not have to cover each item in detail, but if the title of the item does not make it clear enough what is planned to be discussed, it should contain a short description of the intent.

- We measure the commitment of the minute takers by the quality of their notes they took during the meetings, which can be seen in the content, the amount and the style of the notes.

- The quality of minutes is judged by:
  - Conciseness and coverage of the main points discussed during the meeting.
  - Each item in the agenda should be included.
  - All feedback by the TA is clearly noted down.
  - Action points that were decided in the meeting are marked down, with (preliminary) deadlines and responsible singleUser/people for that action point.

## Meetings:
### How often will you meet as a group?

- A meeting as a group is not necessarily a physical meeting. Meeting in Discord or another platform is acceptable if it is more convenient or required.

- After each TA meeting, a short session will be held, to discuss things that were said and figured out during the TA meeting.

- At least one more meeting per week. During it, we would discuss how the work is going, how everyone feels, and confirm the upcoming deadlines.
  - As everyone will have the goal to keep the others up to date with their date, the topics to be covered in this meeting could be already talked about. Thus, despite the possible occurrence of the above-mentioned meeting, an additional one concerning the upcoming week's planning will be held. During it, the team would discuss what should be accomplished in the week to come. Also, team members will create the next *milestone*, will create the different issues and tasks, and would assign them to different team members.
- Additional meetings can be planned, either at the end of any meeting/session, or asynchronously on our communication applications (in this case, such a meeting should be planned at least a day in advance, to allow everyone to see the time and date of the meeting)

- In case a meeting is to be canceled – some members get urgent schedule changes, for a reason of course (otherwise seen as being late and even worse – not attending at all), then it would be rescheduled for as soon as possible. However, if the reason is that some work is still undone and there’s just no point in the meeting, then the case is considered as missed deadline kind of, which has its own consequences.

### What preparation is needed for the meetings?

- Everyone should prepare by getting familiar with the main topic which is going to be discussed.

- If there are some additional aspects which should be considered, it would be announced in Discord in advance, so that everyone has time to prepare thoroughly.

## Decision-making:
### How do you make decisions? By majority vote or by consensus?

- A mixture between majority and consensus will be used. In detail, when a decision is up to be taken, if everyone agrees it’s ready!

- If there is one singleUser proposing a different idea, they have their time to try to convince the others.
  - If there is **at least one other singleUser** who “sees their point”, then more time is allocated for discussion until a decision is reached – the process mentioned above is done over and over.
  - If there is **none** seeing the “opposition”’s perspective, then the discussion is closed and a decision is taken.

- Once a decision is made, it is considered binding. All members should comply with it, unless the decision is reversed/amended by the decision-making process above.

- Everyone from the team needs to express their opinion regarding the proposal made by one of the members even though this is presented on Discord or during the meetings. If the proposal is announced on Discord team members should present their point if view within 24h of the Discord post being made.

## Dealing with conflicts: 
### How do you handle conflicts within the group?

- When a conflict occurs, other people inserting their thoughts is crucial.

- Talking to them in a respectful manner and weighing down all the statements and arguments will eventually result in balance. Consult with other group members and ask them for advice.

- Strive to reach a mutual agreement and fix the things that went wrong, rather than proving you were right or your opinion is “true”-er.

## Guidance: 
### What do you expect from the teacher's and/or student assistant’s guidance? What do you want feedback on, on the content or on the collaboration?

- We want regular feedback regarding the content of our project. To aid that, we will seek to upload any content or document in advance to any project deadlines, such that the TA has an opportunity to at least skim it. That is, internal deadlines will be set to accommodate this.

- We also appreciate any feedback on collaboration and teamwork from the TA.

- During each TA meeting, an item in the agenda should be for TA feedback, where members of the team are allowed to ask questions, and the TA can provide concrete or general feedback. This includes questions regarding teamwork and collaboration.

## Consequences: 
### What are the consequences if a participant in the group does not keep the agreements?

- If someone is late to a deadline, we will listen to their reason for missing the deadline.
  - If the reason is reasonable and one-off, no action will be taken. The reason should be presented with at least 6 hours before the "soft" deadline. 
  - If the reason is reasonable, but could be repeated, we will take team effort to avoid missing future deadlines due to this reason, or to reduce future negative consequences whenever this occurs.
  - If a good reason is not given, they will be warned that such behavior is unacceptable and is not to be repeated. Simple explicit warning will encourage the singleUser to uphold their future agreements (due to social dynamics). If this happens multiple times, the issue will eventually be addressed to the TA (to deal at a higher level than what we are capable of).
  - If the "soft" deadline is breached the others members have the rights to shift their own deadlines. The team member who breached the "soft" deadline should be more prompted for receiving help from others.
  - If the "hard" deadline is breached an informal meeting will be organized for explicitly addressing the missed deadline. In this meeting others will fairly evaluate if the explanation for missing the deadline is reasonable or not. In the case the person who breached the deadline feels he/she cannot finish the issue even after he/she asked for help from the others, another member team will take over the merge request.

## Success factors: 
### What makes your team a dream team?

- Flawless working environment.

- Everyone feels safe to commit, to share. Reach “feeling of belonging” to the group.

- We dream. **Big.**
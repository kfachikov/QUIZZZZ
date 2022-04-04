# Backlog
## Functional Requirements
### Must

- solo gameplay button

As a user who wants to play on their own, I must be presented with a "Solo Game" button when I enter the game, as I would like to play on my own to test my knowledge.

- multiplayer gameplay button

As a user who wants to play with others, I must be presented with a "Multiplayer" button when I enter the game, as I would like to be able to compete against other people.

- "Choose the right activity"-type question

As a player, sometimes I must be asked to choose among several (3) activities which is the one with the lowest/highest consumption, as that would make me aware of how energy-inefficient are many of the daily activities.

- different question each round

As a player, I must be asked different questions in each round, as otherwise there would be no point in answering the same ones over and over again.

- several options as answers for multiple-choice questions

As a player, I must be presented a several (preferably 3) choices for a correct answer, so that I can make a fast guess in this "speed game".

- synchronous questions

As a player in a multiplayer game, the currently asked question must be presented to me exactly when it is presented to other players in the current game, as otherwise, the game wouldn't be in sync with other players, or some players might cheat.

- queue

As a user who wants to play with others, I must be able to enter a queue instead of being directly connected to a game, as thus, I must have the opportunity to wait for other players (or, alternatively, be the one who joins while other players are waiting), so that I can play with people competitively with people who I know will join.

- start button is available while waiting in the queue

As a user who wants to play with others, once already in the queue, I must be able to begin the game whenever I want, as that would allow me to start the game when everyone I was waiting for has joined.

- power-ups (Joker cards)

As a player in a multiplayer game, I must be able to use once-per-game abilities (jokers) to give myself some advantage over the other players, so that I can give myself a crucial boost over others when I spot the opportunity, and to make the game more varied and interesting.

- leaderboard for the best-scoring players

As a user who enjoys playing on their own and testing their knowledge, I must be able to see the leaderboard for the best-scoring players so that I can see who is performing the best among all the players and possibly if I am among those who are.

- infinitely big sessions

As a user who wants to play with others, I must be able to able to join a queue, no matter how many users are already in it, so that I can play with as many people as I want.

- assigning a username to each player in solo mode

As a user who wants to play on their own, I must have a username, so that I can recognize myself among all players in the solo player leaderboard.

- assigning a username to each player in multiplayer mode

As a user who wants to play with others, I must have a username, so that I can recognize myself and distinguish among my opponents.

- two players cannot share a username in one game

As a user who wants to play with others, I must have a unique username in a multiplayer environment, as otherwise, it would be impossible to tell apart two players with identical usernames.

- short timing for answering the questions

As a player in a multiplayer game, I must have just a few seconds to answer the question, as this is supposed to game of both knowledge and speed.

- 20 different activities stored in the database

As a player, I must be asked questions about different activities' consumption rates, as that would improve the game drastically in terms of interest and curiosity.

- ranking/leaderboard presented at the end of each multiplayer game

As a player who has just finished a multiplayer game, I must be able to see how my opponents have scored in total after a game, and also, how I placed among them so that I can stay motivated to learn and to re-match them to do better (or to defend the first place if already on top).

### Should:

- images

As a player, I should see an image together with the question text, as that would help me comprehend the question faster.

- timing the questions

As a player, I should see the time remaining to answer the current question, as otherwise, I wouldn’t know when it’s up, and thus, I might not answer in time, thinking there is some more time.

- giving points considering the speed

As a player in a multiplayer game, I should be awarded more points if I answer correctly faster than some other players, because thus some “hierarchy” can be established.

- the speed is "deduced" by how many seconds are left so that the "reduce time" gives more advantage

As a player in a multiplayer game, I should be able to negatively affect the others by using my "reduce time" power-up, so it would be reasonable to consider the speed of an answer depending on the time left for each player.

- using power-ups after answering a question

As a player in a multiplayer game, I should be able to use a once-per-game ability (joker) even after answering a question, as this is a game of speed and I would strive to answer as fast as I can at first.

- a different type of questions

As a player, I should be asked differently phrased questions, as otherwise the game would be pretty monotonous and would easily bore me.

- estimation questions

As a player, I should be asked a question that requires me to estimate a specific value (e.g. power consumption of a certain appliance) at least once, as that would make the game more exciting and intriguing.

- intermediate leaderboard containing the best-scoring players

As a player in a multiplayer game, I should be able to see an intermediate leaderboard that shows top players' names and scores, because I would like to know which players are on top and whether I am among them.

- back option after a game is done

As a user who has finished a game, I should be presented with a "Return Home" option after a game is finished, as that would improve the usability a lot.

- disconnecting

As a player in a multiplayer game, I should see some indication that a player is inactive (disconnected) -- if present in the intermediate leaderboard their username becomes dimmed.

### Could:

- emoji

As a player in a multiplayer game, during the phase when the question is being answered, or after the correct answer is shown, I could be shown some chatMessage emoji, which I could click to react to the question, adding emotional enjoyment to the game.

- see other people's emojis

As a player in a multiplayer game, I could see how other people are reacting to the current questions, and they could see my chatMessages as well, as that would add some interactivity among the players.

- personal position displayed in intermediate leaderboard even if not among the top

As a player in a multiplayer game, I could see my name and position on the intermediate leaderboard, even if I am not among the best players, as I would like to know how I am doing in comparison to my opponents.

- display positioning during game

As a player in a multiplayer game, in addition to my position being shown once in a while on the leaderboard, my position among the players could be shown to me all the time during the game.

- possible to change your answer

As a player, I could make use of changing my answer to a question, as I could change my mind about the right answer to a question.

- pop-ups and songs

As a player, I could appreciate if the game I’m playing could have a catchy background song and attractive design aspects, as they would make the game more remarkable and fun to play.

- using more than one joker per question

As a player in a multiplayer game, I could use more than one once-per-game ability (joker) per question, as it would make the game more challenging for the other players and give me a solid advantage.

- swapping the "remove answer" card into something else (e.g. range-provider) for estimation questions

As a player, if I still have my “remove answer” card, and if the question is an estimation question, I would appreciate it if I could use this card as a hint that gives me some advantage, as I would like to be able to use my Joker in all of the questions.

- immediately join the queue after a multiplayer game

As a user who wants to play with others, I could be able to rejoin the queue - to "begin" a new game just after the current one ends, as that would make it a lot easier for me to play multiple games.

- hints revealing how points are calculated next to the current question

As a player, I could be presented with an option to check how points are calculated on every question so that I have some basic understanding of the game and probably develop some in-game strategies.

- displaying an "alert" when someone has left

As a player in a multiplayer game, I could be alerted if any of my opponents have left, so that I know how many people I compete with.

### Won't:
- chat

As a player in a multiplayer, I don’t want to have a chat during the game, because the game is based on speed and I don't want to get distracted that easily.

- private rooms

As a user who wants to play with others, there is no need for a "private room" feature.

- kicking

As a user who wants to play with others, I should not have the right to kick other players, as a player that stays idle cannot damage our game flow.

- authentication

As a user, I do not need to authenticate myself (by anything other than username), since my username is unique in a multiplayer game, and I do not need to keep track of anything specific to me between games.

## Non-Functional Requirements

- client-server communication
  - When a player answers a question, their answer choice is sent to the server, which then sends an appropriate response, indicating whether the player was correct or not.
  

- 20 activities (e.g. "dishwasher working 20 minutes consumes X amount of energy") stored in the database.
  - The database should store 20 different activities and their energy-consumption level in a proper format, so that different types of questions can accordingly "piece out" the information they require.


- Do not use static fields. Instead, try to always use dependency injection using the constructor Injector variant.


- Test your code.
  - Have all methods tested properly. Use the "Dependency Inversion Principle", so that the tests are specific -- test only a particular functionality.

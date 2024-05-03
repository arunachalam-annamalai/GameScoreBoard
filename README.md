#ScoreBoard lib 

Gradle project use grable commands to build and run.

gradle test or ./gradlew test -- will execute the below TDD class 

There is **TDD** test class which covers all **scenarios** and edge cases which includes validations:
[ScoreBoardTest](src/test/java/com/sportradar/scoreboard/ScoreBoardTest.java)



###There are 4 lib APIs:
1. **startNewGame** - arg (_homeTeamName_, _awayTeamName_) - Start a new game, assuming initial score 0 – 0 and adding it the scoreboard.
   This should capture following parameters:
   a. Home team
   b. Away team
2. **updateScore** - arg (_gameId_, _score_ [:_homeTeamScore_, :_awayTeamScore_]) - the score is an integer array in the order 1. HomeTEamScore, 2. AwayTEamScore. - Update score. This should receive a pair of absolute scores: home team score and away
   team score.
3. **updateGameFinish** - arg (_gameId_) - Finish game currently in progress. This removes a Game from the scoreboard summary.
4. **getCurrentGamesScoreBoard** - returns list of games(HomeTeam with score, AwayTeam with score)- Get a summary of games in progress ordered by their total score. The games with the same
   total score will be returned ordered by the most recently started match in the scoreboard.



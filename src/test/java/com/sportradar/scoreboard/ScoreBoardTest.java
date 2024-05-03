package com.sportradar.scoreboard;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    @Test()
    public void test_start_game_verifyScore() throws Exception {


        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeam = "Germany";
        String awayTeam = "Italy";

        String gameId = scoreBoard.startNewGame(homeTeam, awayTeam);
        List<GameScoreCard> scoreBoardList = scoreBoard.getCurrentGamesScoreBoard();



        assertNotNull(gameId);
        assertNotEquals("", gameId);
        assertEquals(1, scoreBoardList.size());
        assertEquals(gameId, scoreBoardList.get(0).getGameId());
        assertEquals(awayTeam,scoreBoardList.get(0).getAwayTeam());
        assertEquals(homeTeam,scoreBoardList.get(0).getHomeTeam());
        assertEquals(0,scoreBoardList.get(0).getAwayTeamScore());
        assertEquals(0,scoreBoardList.get(0).getHomeTeamScore());


    }

    @Test
    public void test_start_existing_game_verifyError() throws Exception {


        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeam = "Germany";
        String awayTeam = "Italy";

        String gameId = scoreBoard.startNewGame(homeTeam, awayTeam);


        assertNotNull(gameId);
        assertNotEquals("", gameId);

        Exception thrown = null;
        try{
            scoreBoard.startNewGame(homeTeam, awayTeam);
        } catch (Exception e){
            thrown = e;
        }

        assertNotNull(thrown);
        assertEquals(thrown.getMessage(),String.format("There is an on going game between %s and %s ", homeTeam, awayTeam));
    }

    @Test
    public void test_update_game_verifyScore() throws Exception {


        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeam = "Germany";
        String awayTeam = "Italy";

        String gameId = scoreBoard.startNewGame(homeTeam, awayTeam);
        Integer homeTeamScore = 1;
        Integer awayTeamScore = 2;

        assertNotNull(gameId);
        assertNotEquals("", gameId);

        scoreBoard.updateScore(gameId, new Integer[] { homeTeamScore, awayTeamScore});

        List<GameScoreCard> scoreBoardList = scoreBoard.getCurrentGamesScoreBoard();

        assertEquals(1, scoreBoardList.size());
        assertEquals(gameId, scoreBoardList.get(0).getGameId());
        assertEquals(awayTeam,scoreBoardList.get(0).getAwayTeam());
        assertEquals(homeTeam,scoreBoardList.get(0).getHomeTeam());
        assertEquals(awayTeamScore,scoreBoardList.get(0).getAwayTeamScore());
        assertEquals(homeTeamScore,scoreBoardList.get(0).getHomeTeamScore());


    }

    @Test
    public void test_update_multiple_games_verifyScoreBoard() throws Exception {


        ScoreBoard scoreBoard = new ScoreBoard();

        String[][] teamGamesPair = {{"Germany","Italy"}, {"Netherlands", "Spain"} };
        Integer[][] teamGamesPairScores = {{1,2}, {4, 3} };

        String gameId1 = scoreBoard.startNewGame(teamGamesPair[0][0], teamGamesPair[0][1]);
        String gameId2 = scoreBoard.startNewGame(teamGamesPair[1][0], teamGamesPair[1][1]);


        assertNotNull(gameId1);
        assertNotEquals("", gameId1);
        assertNotNull(gameId2);
        assertNotEquals("", gameId2);

        scoreBoard.updateScore(gameId2, teamGamesPairScores[1]);
        scoreBoard.updateScore(gameId1, teamGamesPairScores[0]);

        List<GameScoreCard> scoreBoardList = scoreBoard.getCurrentGamesScoreBoard();

        assertEquals(2, scoreBoardList.size());
        assertEquals(gameId2, scoreBoardList.get(0).getGameId());
        assertEquals(gameId1, scoreBoardList.get(1).getGameId());

        assertEquals(teamGamesPair[1][1],scoreBoardList.get(0).getAwayTeam());
        assertEquals(teamGamesPair[1][0],scoreBoardList.get(0).getHomeTeam());
        assertEquals( teamGamesPairScores[1][1],scoreBoardList.get(0).getAwayTeamScore());
        assertEquals(teamGamesPairScores[1][0],scoreBoardList.get(0).getHomeTeamScore());

        assertEquals(teamGamesPair[0][1],scoreBoardList.get(1).getAwayTeam());
        assertEquals(teamGamesPair[0][0],scoreBoardList.get(1).getHomeTeam());
        assertEquals( teamGamesPairScores[0][1],scoreBoardList.get(1).getAwayTeamScore());
        assertEquals(teamGamesPairScores[0][0],scoreBoardList.get(1).getHomeTeamScore());


    }


    @Test
    public void test_multi_update_multiple_games_verifyScoreBoard() throws Exception {

        ScoreBoard scoreBoard = new ScoreBoard();

        String[][] teamGamesPair = {{"Germany","Italy"}, {"Netherlands", "Spain"} };
        Integer[][] teamGamesPairScores = {{1,2}, {4, 3} };
        Integer[][] teamGamesPairScoresUpdate1 = {{5,3}};

        String gameId1 = scoreBoard.startNewGame(teamGamesPair[0][0], teamGamesPair[0][1]);
        String gameId2 = scoreBoard.startNewGame(teamGamesPair[1][0], teamGamesPair[1][1]);


        assertNotNull(gameId1);
        assertNotEquals("", gameId1);
        assertNotNull(gameId2);
        assertNotEquals("", gameId2);

        scoreBoard.updateScore(gameId2, teamGamesPairScores[1]);
        scoreBoard.updateScore(gameId1, teamGamesPairScores[0]);
        scoreBoard.updateScore(gameId1, teamGamesPairScoresUpdate1[0]);

        List<GameScoreCard> scoreBoardList = scoreBoard.getCurrentGamesScoreBoard();

        assertEquals(2, scoreBoardList.size());
        assertEquals(gameId1, scoreBoardList.get(0).getGameId());
        assertEquals(gameId2, scoreBoardList.get(1).getGameId());

        assertEquals(teamGamesPair[0][1],scoreBoardList.get(0).getAwayTeam());
        assertEquals(teamGamesPair[0][0],scoreBoardList.get(0).getHomeTeam());
        assertEquals( teamGamesPairScoresUpdate1[0][1],scoreBoardList.get(0).getAwayTeamScore());
        assertEquals(teamGamesPairScoresUpdate1[0][0],scoreBoardList.get(0).getHomeTeamScore());

        assertEquals(teamGamesPair[1][1],scoreBoardList.get(1).getAwayTeam());
        assertEquals(teamGamesPair[1][0],scoreBoardList.get(1).getHomeTeam());
        assertEquals( teamGamesPairScores[1][1],scoreBoardList.get(1).getAwayTeamScore());
        assertEquals(teamGamesPairScores[1][0],scoreBoardList.get(1).getHomeTeamScore());
    }

    @Test
    public void test_multi_update_games_sameScore_verifyScoreBoard() throws Exception {

        ScoreBoard scoreBoard = new ScoreBoard();

        String[][] teamGamesPair = {{"Germany","Italy"}, {"Netherlands", "Spain"}, {"Portugal", "Brazil"} };
        Integer[][] teamGamesPairScores = {{1,2}, {4, 3}, {5, 3} };
        Integer[][] teamGamesPairScoresUpdate1 = {{5,3}};

        String gameId1 = scoreBoard.startNewGame(teamGamesPair[0][0], teamGamesPair[0][1]);
        String gameId2 = scoreBoard.startNewGame(teamGamesPair[1][0], teamGamesPair[1][1]);
        Thread.sleep(1);
        String gameId3 = scoreBoard.startNewGame(teamGamesPair[2][0], teamGamesPair[2][1]);


        assertNotNull(gameId1);
        assertNotEquals("", gameId1);
        assertNotNull(gameId2);
        assertNotEquals("", gameId2);
        assertNotNull(gameId3);
        assertNotEquals("", gameId3);

        scoreBoard.updateScore(gameId2, teamGamesPairScores[1]);
        scoreBoard.updateScore(gameId1, teamGamesPairScores[0]);
        scoreBoard.updateScore(gameId3, teamGamesPairScores[2]);
        scoreBoard.updateScore(gameId1, teamGamesPairScoresUpdate1[0]);

        List<GameScoreCard> scoreBoardList = scoreBoard.getCurrentGamesScoreBoard();

        assertEquals(3, scoreBoardList.size());
        assertEquals(gameId1, scoreBoardList.get(0).getGameId());
        assertEquals(gameId3, scoreBoardList.get(1).getGameId());
        assertEquals(gameId2, scoreBoardList.get(2).getGameId());

        assertEquals(teamGamesPair[0][1],scoreBoardList.get(0).getAwayTeam());
        assertEquals(teamGamesPair[0][0],scoreBoardList.get(0).getHomeTeam());
        assertEquals( teamGamesPairScoresUpdate1[0][1],scoreBoardList.get(0).getAwayTeamScore());
        assertEquals(teamGamesPairScoresUpdate1[0][0],scoreBoardList.get(0).getHomeTeamScore());

        assertEquals(teamGamesPair[2][1],scoreBoardList.get(1).getAwayTeam());
        assertEquals(teamGamesPair[2][0],scoreBoardList.get(1).getHomeTeam());
        assertEquals( teamGamesPairScores[2][1],scoreBoardList.get(1).getAwayTeamScore());
        assertEquals(teamGamesPairScores[2][0],scoreBoardList.get(1).getHomeTeamScore());

        assertEquals(teamGamesPair[1][1],scoreBoardList.get(2).getAwayTeam());
        assertEquals(teamGamesPair[1][0],scoreBoardList.get(2).getHomeTeam());
        assertEquals( teamGamesPairScores[1][1],scoreBoardList.get(2).getAwayTeamScore());
        assertEquals(teamGamesPairScores[1][0],scoreBoardList.get(2).getHomeTeamScore());
    }

    @Test
    public void test_finish_multi_update_multiple_games_verifyScoreBoard() throws Exception {

        ScoreBoard scoreBoard = new ScoreBoard();

        String[][] teamGamesPair = {{"Germany","Italy"}, {"Netherlands", "Spain"}, {"Portugal", "Brazil"} };
        Integer[][] teamGamesPairScores = {{1,2}, {4, 3}, {5, 3} };
        Integer[][] teamGamesPairScoresUpdate1 = {{5,3}};

        String gameId1 = scoreBoard.startNewGame(teamGamesPair[0][0], teamGamesPair[0][1]);
        String gameId2 = scoreBoard.startNewGame(teamGamesPair[1][0], teamGamesPair[1][1]);
        String gameId3 = scoreBoard.startNewGame(teamGamesPair[2][0], teamGamesPair[2][1]);


        assertNotNull(gameId1);
        assertNotEquals("", gameId1);
        assertNotNull(gameId2);
        assertNotEquals("", gameId2);
        assertNotNull(gameId3);
        assertNotEquals("", gameId3);

        scoreBoard.updateScore(gameId2, teamGamesPairScores[1]);
        scoreBoard.updateScore(gameId1, teamGamesPairScores[0]);
        scoreBoard.updateScore(gameId3, teamGamesPairScores[2]);
        scoreBoard.updateScore(gameId1, teamGamesPairScoresUpdate1[0]);
        scoreBoard.updateGameFinish(gameId1);

        List<GameScoreCard> scoreBoardList = scoreBoard.getCurrentGamesScoreBoard();

        assertEquals(2, scoreBoardList.size());
        assertEquals(gameId3, scoreBoardList.get(0).getGameId());
        assertEquals(gameId2, scoreBoardList.get(1).getGameId());

        assertEquals(teamGamesPair[2][1],scoreBoardList.get(0).getAwayTeam());
        assertEquals(teamGamesPair[2][0],scoreBoardList.get(0).getHomeTeam());
        assertEquals( teamGamesPairScores[2][1],scoreBoardList.get(0).getAwayTeamScore());
        assertEquals(teamGamesPairScores[2][0],scoreBoardList.get(0).getHomeTeamScore());

        assertEquals(teamGamesPair[1][1],scoreBoardList.get(1).getAwayTeam());
        assertEquals(teamGamesPair[1][0],scoreBoardList.get(1).getHomeTeam());
        assertEquals( teamGamesPairScores[1][1],scoreBoardList.get(1).getAwayTeamScore());
        assertEquals(teamGamesPairScores[1][0],scoreBoardList.get(1).getHomeTeamScore());
    }
}

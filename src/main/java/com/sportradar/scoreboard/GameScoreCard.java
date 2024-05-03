package com.sportradar.scoreboard;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GameScoreCard {

    private final String gameId;
    private final String homeTeam;
    private final String awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Integer totalScore;
    private final Long startDateTime;

    public GameScoreCard(String homeTeam, String awayTeam, Integer homeTeamScore, Integer awayTeamScore, Integer totalScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.totalScore = totalScore;
        this.gameId = homeTeam + awayTeam;
        this.startDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public String getGameId() {
        return gameId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }



    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Long getStartDateTime() {
        return startDateTime;
    }

    @Override
    public String toString() {
        return "GameScoreCard{" +
                "gameId='" + gameId + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", homeTeamScore=" + homeTeamScore +
                ", awayTeamScore=" + awayTeamScore +
                ", totalScore=" + totalScore +
                ", startDateTime=" + startDateTime +
                '}';
    }
}

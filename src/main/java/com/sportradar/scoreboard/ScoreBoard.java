package com.sportradar.scoreboard;


import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public class ScoreBoard {



//        K: homeCountry , againstcountry, score1, score2 , v:() -> totalScore
//            HashMap
//            hc + ac : (hc:sc1-ac:sc2-totalScore);
//        new totalscore, nsc1, nsc2
//
//        TreeMap
//        totalScore : (hc:sc1-ac:sc2-totalScore);


    private Map<String, GameScoreCard> gameScoreMap = new HashMap<>();
    private SortedMap<Integer, TreeSet<GameScoreCard>> scoreBoardMap = new ConcurrentSkipListMap<>(Collections.reverseOrder());

    private Comparator<GameScoreCard> gameScoreCardComparator  = Comparator.comparingLong(GameScoreCard::getStartDateTime).thenComparing(GameScoreCard::getHomeTeam);

    public void updateScore(String gameId, Integer[] scores) throws Exception {

        GameScoreCard ugsc = gameScoreMap.get(gameId);
        if (gameId == null || ugsc == null) {
            throw new Exception(String.format("There is an no game going with gameId: %s", gameId));
        }

        if (scores.length > 2 || scores.length < 2 || scores[0] < 0 || scores[1] < 0) {
            throw new Exception(
                    String.format("scores input should have 2 values and can't be negative, " +
                            "Ex: [1, 2] - 1 - homeTeam, 2- awayTeam"));
        }


        Integer homeScore = scores[0];
        Integer awayScore = scores[1];



        Integer prevScore = ugsc.getTotalScore();
        int totalScore = homeScore + awayScore;
        final String gameID = ugsc.getGameId();
        ugsc.setHomeTeamScore(homeScore);
        ugsc.setAwayTeamScore(awayScore);
        ugsc.setTotalScore(totalScore);
        TreeSet<GameScoreCard> gameScoreCardSortedSet = scoreBoardMap.get(prevScore);
        TreeSet<GameScoreCard> newScoreCardSortedSet = gameScoreCardSortedSet == null ?
                new TreeSet<>(gameScoreCardComparator) : gameScoreCardSortedSet.stream().filter(gsc -> !(gsc.getGameId().equals(gameID)
        )).collect(Collectors.toCollection(() -> new TreeSet<>(gameScoreCardComparator)));
        scoreBoardMap.put(prevScore, newScoreCardSortedSet);

        TreeSet<GameScoreCard> addScoreCardSortedSet = scoreBoardMap.get(totalScore);
        if(null == addScoreCardSortedSet){
            addScoreCardSortedSet = new TreeSet<>(gameScoreCardComparator);
        }
        addScoreCardSortedSet.add(ugsc);
        scoreBoardMap.put(totalScore, addScoreCardSortedSet);

    }

    public String startNewGame(String homeTeam, String awayTeam) throws Exception {

        GameScoreCard newGameScoreCard = new GameScoreCard(homeTeam, awayTeam, 0, 0, 0);
        GameScoreCard existGameScoreCard = gameScoreMap.get(newGameScoreCard.getGameId());
        if(existGameScoreCard != null){
            throw new Exception(String.format("There is an on going game between %s and %s ", homeTeam, awayTeam));
        }

        gameScoreMap.put(newGameScoreCard.getGameId(), newGameScoreCard);
        scoreBoardMap.computeIfAbsent(0, k -> new TreeSet<>(gameScoreCardComparator)).add(newGameScoreCard);
        return newGameScoreCard.getGameId();

    }

    public boolean updateGameFinish(String gameId){

        GameScoreCard ugsc = gameScoreMap.get(gameId);

        if(ugsc != null){
            gameScoreMap.remove(gameId);
            Integer prevScore = ugsc.getTotalScore();
            final String gameID = ugsc.getGameId();
            SortedSet<GameScoreCard> gameScoreCardList = scoreBoardMap.get(prevScore);
            if( gameScoreCardList != null) {
                TreeSet<GameScoreCard> newScoreCardSortedSet = gameScoreCardList.stream().filter(gsc -> !(gsc.getGameId().equals(gameID)
                )).collect(Collectors.toCollection(()-> new TreeSet<>(gameScoreCardComparator)));
                if(newScoreCardSortedSet == null || newScoreCardSortedSet.isEmpty()) {
                    scoreBoardMap.remove(prevScore);
                } else {
                    scoreBoardMap.put(prevScore, newScoreCardSortedSet);
                }
                return true;
            }
        }
        return false;

    }

    public  List<GameScoreCard> getCurrentGamesScoreBoard() {
        List<GameScoreCard> gameScoreCardList = new ArrayList<>();
        List<Integer> scoresInDescOrdList = new ArrayList<>(this.scoreBoardMap.keySet());


        for(Integer key : scoresInDescOrdList){

            TreeSet<GameScoreCard> collGameScoreCardSortedSet = this.scoreBoardMap.get(key);

            if(collGameScoreCardSortedSet != null){
                List<GameScoreCard> sortedList = collGameScoreCardSortedSet.stream().toList();
                gameScoreCardList.addAll(sortedList);
            }

        }

        return gameScoreCardList.stream().collect(Collectors.toUnmodifiableList());
    }







}

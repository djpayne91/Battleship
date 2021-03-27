package main;

import main.model.*;

public class GameController {

    private BattleshipGame game;
    private GameRules rules;

    public void startNewGame(GameRules gameRules){
        Player player1 = new Player(gameRules.getShipList(), new Board(10,10));
        Player player2 = new Player(gameRules.getShipList(), new Board(10,10));
        this.game = new BattleshipGame(player1, player2);
        this.rules = gameRules;
    }


    public boolean getShouldUpdateView(){
        return rules.getShouldViewUpdate();
    }

    public Player getNextPlayer(){
        switch (rules.getNextPlayer()){
            case GameRules.PLAYER_ONE:{
                return game.getPlayerOne();
            }
            case GameRules.PLAYER_TWO:{
                return game.getPlayerTwo();
            }
            default:{
                throw new RuntimeException("Error getting next player.");
            }
        }
    }

    public boolean getGameShouldEnd(){
        return game.getWinner() != null;
    }

}

package com.mrb.alias.game;

import com.mrb.alias.results.Game;

/**
 * Created by chv on 15.04.2016.
 */
public interface GameView {

    void showWord(String word);
    void startTimer(int time);
    void navigateToRoundResults();
    Game loadGame();
    void saveGame(Game game);
}

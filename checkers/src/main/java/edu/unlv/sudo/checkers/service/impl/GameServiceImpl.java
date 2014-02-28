package edu.unlv.sudo.checkers.service.impl;

import edu.unlv.sudo.checkers.model.Team;
import edu.unlv.sudo.checkers.service.GameService;

/**
 * This {@link GameService} talks to the checkers API.
 */
public class GameServiceImpl implements GameService {

    private static final String BASE_URL = "http://23.253.42.115:2020/checkers/game/";

    @Override
    public void joinGame(String gameId, Team team, Listener listener) {
        //TODO: implement me!
    }

    @Override
    public void newGame(Team team, Listener listener) {
        //TODO: implement me!
    }
}

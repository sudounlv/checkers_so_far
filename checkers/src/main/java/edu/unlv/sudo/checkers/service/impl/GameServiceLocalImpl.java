package edu.unlv.sudo.checkers.service.impl;

import java.util.HashMap;
import java.util.Map;

import edu.unlv.sudo.checkers.model.Board;
import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;
import edu.unlv.sudo.checkers.service.GameService;

/**
 * This {@link GameService} locally stores games and runs them.
 */
public class GameServiceLocalImpl implements GameService {

    //TODO: this is a temporary placeholder - remove it?

    final Map<String, Game> games = new HashMap<>();
    int gameNum = 0;

    @Override
    public Game move(final String gameId, final Board board) {
        final Game game = games.get(gameId);
        final Team turn = game.getTurn() == Team.RED ? Team.BLACK : Team.RED;

        return new Game(gameId, board, turn);
    }

    @Override
    public Game joinGame(final String gameId, final Team team) {
        return games.get(gameId);
    }

    @Override
    public Game newGame() {
        final String gameId = "game" + gameNum++;
        final Game game = new Game(gameId, Team.BLACK);

        games.put(gameId, game);

        return game;
    }

    @Override
    public Game awaitMove(final String gameId) {
        return games.get(gameId);
    }
}

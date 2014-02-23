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
    public void move(final String gameId, final Board board, final Listener listener) {
        final Game game = games.get(gameId);
        final Team turn = game.getTurn() == Team.RED ? Team.BLACK : Team.RED;

        listener.onGame(new Game(gameId, board, turn));
    }

    @Override
    public void joinGame(final String gameId, final Team team, final Listener listener) {
        listener.onGame(games.get(gameId));
    }

    @Override
    public void newGame(final Team team, final Listener listener) {
        final String gameId = "game" + gameNum++;
        final Game game = new Game(gameId, Team.BLACK);

        games.put(gameId, game);

        listener.onGame(game);
    }

    @Override
    public void update(final String gameId, final Listener listener) {
        listener.onGame(games.get(gameId));
    }
}

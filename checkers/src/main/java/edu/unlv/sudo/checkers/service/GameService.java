package edu.unlv.sudo.checkers.service;

import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;

/**
 * This service describes the interface between the client and the REST server.
 */
public interface GameService {

    /**
     * Join a game already in progress on a specified team.
     * @param gameId the ID of the game to join
     * @param team the {@link edu.unlv.sudo.checkers.model.Team} to join
     * @param listener the {@link Listener} to handle callbacks
     */
    void joinGame(String gameId, Team team, Listener listener);

    /**
     * Create a new game.
     * @param team the {@link edu.unlv.sudo.checkers.model.Team} to join
     * @param listener the {@link Listener} to handle callbacks
     */
    void newGame(Team team, Listener listener);


    /**
     * A listener for games.
     */
    interface Listener {

        /**
         * Called when a game is available.
         * @param game the {@link edu.unlv.sudo.checkers.model.Game} that became available
         */
        void onGame(Game game);

        /**
         * Called when an error occurs retrieving the game.
         * @param exception the {@link Exception}
         */
        void onError(Exception exception);
    }
}

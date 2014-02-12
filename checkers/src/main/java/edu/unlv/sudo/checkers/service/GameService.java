package edu.unlv.sudo.checkers.service;

import edu.unlv.sudo.checkers.model.Board;
import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;

/**
 * This service describes the interface between the client and the REST server.
 */
public interface GameService {

    //TODO: this should probably be async

    /**
     * Issue a move on a game by presenting a new board object.
     * @param gameId the ID of the game in play
     * @param board the {@link Board} object representing the move made
     * @return the game object accepted by the server
     * @throws Exception if an error occurs during request
     */
    Game move(String gameId, Board board) throws Exception;

    /**
     * Join a game already in progress on a specified team.
     * @param gameId the ID of the game to join
     * @param team the {@link Team} to join
     * @return the game board
     * @throws Exception if an error occurs during request
     */
    Game joinGame(String gameId, Team team) throws Exception;

    /**
     * Create a new game.
     * @return the newly created game
     * @throws Exception if an error occurs during request
     */
    Game newGame() throws Exception;

    /**
     * Wait for the server to respond with a move by the opponent.
     * @param gameId the ID of the game in play
     * @return the current server game
     * @throws Exception if an error occurs during request
     */
    Game awaitMove(String gameId) throws Exception;
}

package edu.unlv.sudo.checkers.service.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import edu.unlv.sudo.checkers.CheckersBoard;
import edu.unlv.sudo.checkers.model.Board;
import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;
import edu.unlv.sudo.checkers.service.GameService;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

/**
 * This {@link GameService} uses a RESTful interface to persist and read data.
 */
public class GameServiceRESTImpl implements GameService {

    private static final String BASE_URL = "http://23.253.42.115:2020/checkers/game/";
    private static final String CREATE_ENDPOINT = "create/";
    private static final String READ_ENDPOINT = "";
    private static final String MOVE_ENDPOINT = "move/";

    private static final String URL_PARAM_DEVICE_ID = "deviceId";
    private static final String URL_PARAM_TEAM = "color";

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void move(final String gameId, final Board board, final Listener listener) {
        final JSONObject requestBody;
        try {
            requestBody = new JSONObject(mapper.writeValueAsString(board));
        } catch (Exception e) {
            listener.onError(e);
            return;
        }

        final String url = BASE_URL + MOVE_ENDPOINT + gameId;

        issuePost(url, requestBody, listener);
    }

    @Override
    public void joinGame(final String gameId, final Team team, final Listener listener) {

    }

    @Override
    public void newGame(final Team team, final Listener listener) {
        final String url = BASE_URL + CREATE_ENDPOINT
                        + "?" + URL_PARAM_DEVICE_ID + "=" + CheckersBoard.getDeviceUid()
                        + "&" + URL_PARAM_TEAM + "=" + team.name();

        issueGet(url, listener);
    }

    @Override
    public void update(final String gameId, final Listener listener) {
        final String url = BASE_URL + READ_ENDPOINT + gameId;
        issueGet(url, listener);
    }

    /**
     * Issue a GET request to the game server.
     * @param url the URL to make the request to
     * @param listener the callback listener
     */
    private void issueGet(final String url, final Listener listener) {
        issuePost(url, null, listener);
    }

    /**
     * Issue a POST request to the game server.
     * @param url the URL to make the request to
     * @param body the {@link JSONObject} body of the request
     * @param listener the callback listener
     */
    private void issuePost(final String url, final JSONObject body, final Listener listener) {
        final JsonObjectRequest request = new JsonObjectRequest(
                url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listener.onGame(parseGame(response));
                        } catch (IOException e) {
                            listener.onError(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                }
        );

        CheckersBoard.getRequestQueue().add(request);
    }

    /**
     * Parse a {@link JSONObject} into a {@link Game} object.
     * @param jsonObject the {@link JSONObject} to parse
     * @return the {@link Game} result
     * @throws IOException if unable to parse the JSON
     */
    private Game parseGame(final JSONObject jsonObject) throws IOException {
        return mapper.readValue(jsonObject.toString(), Game.class);
    }
}

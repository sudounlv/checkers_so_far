package edu.unlv.sudo.checkers.service.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;

import edu.unlv.sudo.checkers.CheckersBoard;
import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;
import edu.unlv.sudo.checkers.service.GameService;

/**
 * This {@link GameService} talks to the checkers API.
 */
public class GameServiceImpl implements GameService {

    private static final String BASE_URL = "http://23.253.42.115:2020/checkers/game/";
    private static final String CREATE_ENDPOINT = "create/";
    private static final String JOIN_ENDPOINT = "join/";

    private static final String URL_PARAM_DEVICE_ID = "deviceId";
    private static final String URL_PARAM_TEAM = "color";

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void joinGame(String gameId, Team team, Listener listener) {
        final String url = BASE_URL + gameId + "/"
                + JOIN_ENDPOINT + "/" + CheckersBoard.getDeviceUid();

        issuePost(url, null, listener);
    }

    @Override
    public void newGame(Team team, Listener listener) {
        final String url = BASE_URL + CREATE_ENDPOINT
                + "?" + URL_PARAM_DEVICE_ID + "=" + CheckersBoard.getDeviceUid()
                + "&" + URL_PARAM_TEAM + "=" + team.name();

        issueGet(url, listener);
    }

    @Override
    public void move(Game game, Listener listener) {
        //TODO: implement me!
    }

    @Override
    public void update(String gameId, Listener listener) {
        final String url = BASE_URL + gameId;

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
     * @param body the {@link org.json.JSONObject} body of the request
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
     * Parse a {@link JSONObject} into a {@link edu.unlv.sudo.checkers.model.Game} object.
     * @param jsonObject the {@link JSONObject} to parse
     * @return the {@link edu.unlv.sudo.checkers.model.Game} result
     * @throws IOException if unable to parse the JSON
     */
    private Game parseGame(final JSONObject jsonObject) throws IOException {
        return mapper.readValue(jsonObject.toString(), Game.class);
    }
}

package edu.unlv.sudo.checkers.service.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import edu.unlv.sudo.checkers.model.Board;
import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;
import edu.unlv.sudo.checkers.service.GameService;

//import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

/**
 * This {@link GameService} uses a RESTful interface to persist and read data.
 */
public class GameServiceRESTImpl implements GameService {

    //TODO: make this actually do something

    private static final String BASE_URL = "http://some.server.com/api/";

//    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Game move(final String gameId, final Board board) throws Exception {
//        final JSONObject requestBody = new JSONObject(mapper.writeValueAsString(board));
//
//        final JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.PUT,
//                BASE_URL + "move/" + gameId,
//                requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );

        return null;
    }

    @Override
    public Game joinGame(final String gameId, final Team team) {
        return null;
    }

    @Override
    public Game newGame() {
        return null;
    }

    @Override
    public Game awaitMove(final String gameId) {
        return null;
    }
}

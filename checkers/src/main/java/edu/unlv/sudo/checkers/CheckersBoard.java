package edu.unlv.sudo.checkers;

import android.app.AlertDialog;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.model.Team;
import edu.unlv.sudo.checkers.service.GameService;
import edu.unlv.sudo.checkers.service.impl.GameServiceRESTImpl;
import edu.unlv.sudo.checkers.views.BoardView;

public class CheckersBoard extends ActionBarActivity {

    private BoardView boardView;
    private GameService gameService = new GameServiceRESTImpl();
    private Game game;

    private static String deviceUid;
    private static RequestQueue requestQueue;

    /**
     * @return the Device UID.
     */
    public static String getDeviceUid() {
        return deviceUid;
    }

    /**
     * @return the {@link RequestQueue} for this activity.
     */
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_board);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CheckersFragment())
                    .commit();
        }

        deviceUid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();

        final Context context = this;

        gameService.newGame(Team.RED, new GameService.Listener() {
            @Override
            public void onGame(Game g) {
                game = g;
                if (boardView != null) {
                    boardView.setGame(game);
                }
            }

            @Override
            public void onError(Exception exception) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setTitle("Error");
                alertBuilder.setMessage("Unable to create new game on server: " + exception.getMessage());
                alertBuilder.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.checkers_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_join) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class CheckersFragment extends Fragment {

        public CheckersFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_checkers_board, container, false);

            boardView = (BoardView) rootView.findViewById(R.id.checkers_board);
            boardView.setGame(game);
            return rootView;
        }
    }

}

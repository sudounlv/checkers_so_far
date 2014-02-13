package edu.unlv.sudo.checkers;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.service.GameService;
import edu.unlv.sudo.checkers.service.impl.GameServiceLocalImpl;
import edu.unlv.sudo.checkers.views.BoardView;

public class CheckersBoard extends ActionBarActivity {

    private static BoardView boardView;
    private static GameService gameService = new GameServiceLocalImpl();
    private static Game game;

    public CheckersBoard() throws Exception {
        game = gameService.newGame();
    }

    //TODO: construct a much nicer view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_board);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CheckersFragment())
                    .commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
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
    public static class CheckersFragment extends Fragment {

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

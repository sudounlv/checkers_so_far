package edu.unlv.sudo.checkers.callback;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.unlv.sudo.checkers.model.Game;
import edu.unlv.sudo.checkers.service.GameService;

/**
 * This {@link Future} handles game responses.
 */
public class GameFuture implements Future<Game> {

    private final Object synchObject = new Object();

    private List<GameService.Listener> listeners = new LinkedList<>();

    private boolean done = false;
    private boolean error = false;

    private Game game;
    private Exception exception;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public Game get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (synchObject) {
            if (!done && !error) {
                final long millis = TimeUnit.MILLISECONDS.convert(timeout, unit);
                synchObject.wait(millis);
            }

            if (!done) {
                throw new TimeoutException("Request did not complete in required time");
            }

            if (error) {
                throw new ExecutionException(exception);
            }

            return game;
        }
    }

    @Override
    public Game get() throws InterruptedException, ExecutionException {
        synchronized (synchObject) {
            if (!done && !error) {
                synchObject.wait();
            }

            if (error) {
                throw new ExecutionException(exception);
            }

            return game;
        }
    }

    public void get(final GameService.Listener listener) {
        synchronized (synchObject) {
            if (done) {
                listener.onGame(game);
            } else if (error) {
                listener.onError(exception);
            } else {
                listeners.add(listener);
            }
        }
    }

    public void set(final Game game) {
        synchronized (synchObject) {
            this.game = game;
            this.done = true;

            for (GameService.Listener listener : listeners) {
                listener.onGame(game);
            }

            synchObject.notifyAll();
        }
    }

    public void error(final Exception e) {
        synchronized (synchObject) {
            this.exception = e;
            this.error = true;

            for (GameService.Listener listener : listeners) {
                listener.onError(e);
            }

            synchObject.notifyAll();
        }
    }
}

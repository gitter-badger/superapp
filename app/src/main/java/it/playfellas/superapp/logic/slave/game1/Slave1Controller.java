package it.playfellas.superapp.logic.slave.game1;

import com.squareup.otto.Subscribe;

import it.playfellas.superapp.events.game.BeginStageEvent;
import it.playfellas.superapp.events.game.EndGameEvent;
import it.playfellas.superapp.events.game.EndStageEvent;
import it.playfellas.superapp.events.game.StartGameEvent;
import it.playfellas.superapp.events.game.ToggleGameModeEvent;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.logic.slave.SlaveController;
import it.playfellas.superapp.network.TenBus;

/**
 * Created by affo on 03/08/15.
 */
public abstract class Slave1Controller extends SlaveController {
    private static final String TAG = SlaveController.class.getSimpleName();
    private boolean dispenserToggle;
    private IntruderTileDispenser normalDispenser;
    private IntruderTileDispenser specialDispenser;

    // Object to be registered on `TenBus`.
    // We need it to make extending classes inherit
    // `@Subscribe` methods.
    private Object busListener;

    public Slave1Controller(TileSelector ts) {
        super(ts);
        normalDispenser = getDispenser(ts);
        specialDispenser = getSpecialDispenser(ts, normalDispenser);

        busListener = new Object() {
            @Subscribe
            public void onGameChange(ToggleGameModeEvent e) {
                toggleDispenser();
            }
        };
        TenBus.get().register(busListener);
    }

    /**
     * @return a new `TileDispenser` for this controller
     */
    @Override
    protected abstract IntruderTileDispenser getDispenser(TileSelector ts);

    /**
     * @return a new `TileDispenser` for special game mode
     */
    protected abstract IntruderTileDispenser getSpecialDispenser(TileSelector ts, IntruderTileDispenser normal);

    protected synchronized boolean isNormalMode() {
        return dispenserToggle;
    }

    private synchronized void toggleDispenser() {
        dispenserToggle = !dispenserToggle;
        if (dispenserToggle) {
            setDispenser(normalDispenser);
        } else {
            setDispenser(specialDispenser);
        }
    }

    @Override
    protected synchronized void onBeginStage(BeginStageEvent e) {
        dispenserToggle = true;
        setDispenser(normalDispenser);
    }

    /**
     * Hooks unused
     */

    @Override
    protected void onStartGame(StartGameEvent e) {

    }

    @Override
    protected void onEndStage(EndStageEvent e) {

    }

    @Override
    protected void onEndGame(EndGameEvent e) {

    }
}

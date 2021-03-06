package it.playfellas.superapp.logic.slave.game23;

import android.util.Log;

import it.playfellas.superapp.events.game.BeginStageEvent;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.tiles.Tile;

/**
 * Created by affo on 07/08/15.
 */
public class Slave2Controller extends Slave23Controller {
    private static final String TAG = Slave2Controller.class.getSimpleName();
    private SizeDispenser dispenser;
    private int rightPtr;

    public Slave2Controller(TileSelector ts) {
        super();
        this.dispenser = new SizeDispenser(ts);
    }

    @Override
    protected void onBeginStage(BeginStageEvent e) {
        this.rightPtr = 0;
    }

    @Override
    protected boolean isTileRight(Tile t) {
        if (rightPtr >= getBaseTiles().length) {
            Log.d(TAG, "The stage should have been already finished: " + rightPtr + " right answers > " + getBaseTiles().length);
            return false;
        }

        if (t.equals(getBaseTiles()[rightPtr])) {
            synchronized (this) {
                rightPtr++;
            }
            return true;
        }
        return false;
    }

    @Override
    protected TileDispenserWBaseTiles getDispenser() {
        return dispenser;
    }
}

package it.playfellas.superapp.logic.master.game23;

import it.playfellas.superapp.events.EventFactory;
import it.playfellas.superapp.logic.Config;
import it.playfellas.superapp.logic.master.MasterController;
import it.playfellas.superapp.logic.tiles.Tile;
import it.playfellas.superapp.network.TenBus;

/**
 * Created by affo on 02/09/15.
 */
public abstract class Master23Controller extends MasterController {
    public Master23Controller(Config conf) {
        super(conf);
    }

    @Override
    protected void onBeginStage() {
        Tile[] tiles = newBaseTiles();
        // broadcast baseTiles for this stage
        TenBus.get().post(EventFactory.baseTiles(tiles));
    }

    protected abstract Tile[] newBaseTiles();
}

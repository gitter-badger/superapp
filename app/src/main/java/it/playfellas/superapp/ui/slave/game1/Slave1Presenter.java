package it.playfellas.superapp.ui.slave.game1;

import com.squareup.otto.Subscribe;

import java.util.Random;

import it.playfellas.superapp.events.game.RTTUpdateEvent;
import it.playfellas.superapp.events.tile.NewTileEvent;
import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.logic.slave.game1.Slave1Color;
import it.playfellas.superapp.logic.slave.game1.Slave1Controller;
import it.playfellas.superapp.network.TenBus;
import it.playfellas.superapp.ui.slave.TileDisposer;

/**
 * Created by Stefano Cappa on 30/07/15.
 */
public class Slave1Presenter {

    private SlaveGame1Fragment slaveGame1Fragment;
    private Slave1Controller slave1;
    private Config1 config;
    private TileSelector db;
    private TileDisposer tileDisposer;

    private TenBus bus = TenBus.get();

    public Slave1Presenter() {
        bus.register(this);
    }

    public void onTakeView(TileSelector db, SlaveGame1Fragment slaveGame1Fragment, Config1 config) {
        this.slaveGame1Fragment = slaveGame1Fragment;
        this.config = config;
        this.db = db;
    }

    public void initController() {
        switch (this.config.getRule()) {
            default:
                slave1 = new Slave1Color(this.db);
                slave1.init();
                break;
            case 2:
                break;
        }

        this.tileDisposer = new TileDisposer(slave1, config) {
            @Override
            protected boolean shouldIStayOrShouldISpawn() {
                //TODO implement real tiledisposer
                return true;
            }
        };

        this.tileDisposer.start();
    }


    @Subscribe
    public void onNewTileEvent(NewTileEvent event) {
        Random r = new Random();
        if (r.nextBoolean()) {
            slaveGame1Fragment.getConveyorUp().addTile(event.getTile());
        } else {
            slaveGame1Fragment.getConveyorDown().addTile(event.getTile());
        }
    }

    @Subscribe
    public void onRttEvent(RTTUpdateEvent e) {
        slaveGame1Fragment.getConveyorUp().changeSpeed(e.getRtt());
        slaveGame1Fragment.getConveyorDown().changeSpeed(e.getRtt());
    }
}

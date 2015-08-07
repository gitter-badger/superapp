package it.playfellas.superapp.ui.slave.game1;

import com.squareup.otto.Subscribe;

import java.util.Random;

import it.playfellas.superapp.events.game.RTTUpdateEvent;
import it.playfellas.superapp.events.game.ToggleGameModeEvent;
import it.playfellas.superapp.events.tile.NewTileEvent;
import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.logic.slave.game1.Slave1Color;
import it.playfellas.superapp.logic.slave.game1.Slave1ColorAgain;
import it.playfellas.superapp.logic.slave.game1.Slave1Controller;
import it.playfellas.superapp.logic.slave.game1.Slave1Direction;
import it.playfellas.superapp.logic.slave.game1.Slave1Shape;
import it.playfellas.superapp.logic.tiles.TileColor;
import it.playfellas.superapp.logic.tiles.TileDirection;
import it.playfellas.superapp.logic.tiles.TileShape;
import it.playfellas.superapp.network.TenBus;
import it.playfellas.superapp.ui.slave.SlavePresenter;
import it.playfellas.superapp.ui.slave.TileDisposer;

/**
 * Created by Stefano Cappa on 30/07/15.
 */
public class Slave1Presenter extends SlavePresenter {

    private SlaveGame1Fragment slaveGame1Fragment;
    private Config1 config;
    private TileSelector db;
    private TileDisposer tileDisposer;

    private TenBus bus = TenBus.get();

    public Slave1Presenter(TileSelector db, SlaveGame1Fragment slaveGame1Fragment, Config1 config) {
        bus.register(this);
        this.slaveGame1Fragment = slaveGame1Fragment;
        this.config = config;
        this.db = db;
    }

    @Override
    protected void newTileEvent(NewTileEvent event) {
        this.addTileToConveyors(event);
    }

    public void initControllerColor(TileColor tileColor) {
        Slave1Controller slave1 = null;
        if (config.getRule() == 1) { //called colorAgain or "Sagome" or shape
            slave1 = new Slave1ColorAgain(this.db, tileColor);
        } else {
            //in all other cases use rule 0!
            //rule 0: color (config.getRule() == 0)
            slave1 = new Slave1Color(this.db, tileColor);
        }
        this.startTileDisposer(slave1);
    }

    public void initControllerDirection(TileDirection tileDirection) {
        Slave1Controller slave1 = new Slave1Direction(this.db, tileDirection);
        this.startTileDisposer(slave1);
    }

    public void initControllerShape(TileShape tileShape) {
        Slave1Controller slave1 = new Slave1Shape(this.db, tileShape);
        this.startTileDisposer(slave1);
    }

    public void startTileDisposer(Slave1Controller slave1) {
        slave1.init();
        this.tileDisposer = new TileDisposer(slave1, config) {
            @Override
            protected boolean shouldIStayOrShouldISpawn() {
                Random r = new Random();
                if ((r.nextInt(4)) == 3) {
                    return false;       //p=1/4
                } else { //numbers 0,1,2
                    return true;        //p=3/4
                }
            }
        };
        this.tileDisposer.start();
    }

    @Subscribe
    public void onRttEvent(RTTUpdateEvent e) {
        slaveGame1Fragment.getConveyorUp().changeSpeed(e.getRtt());
        slaveGame1Fragment.getConveyorDown().changeSpeed(e.getRtt());
    }

    @Subscribe
    public void onToggleGameMode(ToggleGameModeEvent e) {
        slaveGame1Fragment.notifyMessage("Il gioco si è invertito");
    }

    private void addTileToConveyors(NewTileEvent event) {
        Random r = new Random();
        if (r.nextBoolean()) {
            slaveGame1Fragment.getConveyorUp().addTile(event.getTile());
        } else {
            slaveGame1Fragment.getConveyorDown().addTile(event.getTile());
        }
    }
}

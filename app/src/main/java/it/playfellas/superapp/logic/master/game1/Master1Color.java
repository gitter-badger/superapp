package it.playfellas.superapp.logic.master.game1;

import it.playfellas.superapp.RandomUtils;
import it.playfellas.superapp.events.EventFactory;
import it.playfellas.superapp.events.game.StartGameEvent;
import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.tiles.TileColor;

/**
 * Created by affo on 07/08/15.
 */
public class Master1Color extends Master1Controller {
    private Config1 conf;
    private TileColor baseColor;

    public Master1Color(Config1 conf) {
        super(conf);
        this.conf = conf;
        TileColor[] colors = {
                TileColor.VIOLET,
                TileColor.YELLOW,
                TileColor.GREEN,
                TileColor.ORANGE,
                TileColor.RED
        };
        this.baseColor = RandomUtils.choice(colors);
    }

    @Override
    protected void onBeginStage() {

    }

    @Override
    protected void onEndStage() {

    }

    @Override
    protected StartGameEvent getNewGameEvent() {
        return EventFactory.startGame1Color(conf, baseColor);
    }
}

package it.playfellas.superapp.ui.master.game2;

import it.playfellas.superapp.logic.Config2;
import it.playfellas.superapp.logic.master.MasterController;
import it.playfellas.superapp.logic.master.game23.Master2Alternate;
import it.playfellas.superapp.logic.master.game23.Master2Decreasing;
import it.playfellas.superapp.logic.master.game23.Master2Growing;
import it.playfellas.superapp.logic.master.game23.Master2Random;
import it.playfellas.superapp.network.TenBus;
import it.playfellas.superapp.ui.master.GamePresenter;

public class Game2Presenter extends GamePresenter {
    private static final String TAG = Game2Presenter.class.getSimpleName();
    private Game2Fragment fragment;
    private Config2 config2;
    private MasterController master;

    public Game2Presenter(Game2Fragment fragment, Config2 config) {
        super(fragment, config);

        this.fragment = fragment;
        this.config2 = config;

        TenBus.get().register(this);

        //init() creates the master in superclass, based on config2.getRule()
        //ATTENTION: if you call this line after super.getMaster(),
        //you'll get a NullPointerException!!!
        //Obviously, the master is an instance of the correct concrete master.
        super.init();
        //now that i have the master in superclass i can get its and use in this class
        //getMaster returns a generic MasterController, but it created using a concrete master, based on rule
        //for this reason it will work!!!
        this.master = super.getMaster();

        this.fragment.initCentralImage(config.getNoStages());
        this.master.beginStage();
    }

    @Override
    protected MasterController newMasterController() {
        switch (config2.getGameMode()) {
            default:
            case 0:
                return new Master2Growing(Game2Fragment.tileSelector, config2);
            case 1:
                return new Master2Decreasing(Game2Fragment.tileSelector, config2);
            case 2:
                return new Master2Alternate(Game2Fragment.tileSelector, config2);
            case 3:
                return new Master2Random(Game2Fragment.tileSelector, config2);
        }
    }
}

package it.playfellas.superapp.ui.slave.game1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.logic.tiles.TileShape;

/**
 * Created by Stefano Cappa on 30/07/15.
 */
public class SlaveGame1ShapeFragment extends SlaveGame1Fragment {
    public static final String TAG = "SlaveGame1ColorFragment";

    private static TileShape tShape;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     * You can't put this method in a superclass because you can't create a static abstract method.
     */
    public static SlaveGame1ShapeFragment newInstance(TileSelector ts, Config1 config1, TileShape tileShape, Bitmap photoBitmap) {
        SlaveGame1Fragment.init(ts, config1, photoBitmap);
        SlaveGame1ShapeFragment fragment = new SlaveGame1ShapeFragment();
        tShape = tileShape;
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new Slave1Presenter(db, this, config);
        presenter.initControllerShape(tShape);
    }
}

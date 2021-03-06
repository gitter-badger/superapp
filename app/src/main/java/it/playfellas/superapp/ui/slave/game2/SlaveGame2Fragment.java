package it.playfellas.superapp.ui.slave.game2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import it.playfellas.superapp.InternalConfig;
import it.playfellas.superapp.R;
import it.playfellas.superapp.conveyors.Conveyor;
import it.playfellas.superapp.conveyors.MovingConveyor;
import it.playfellas.superapp.conveyors.SizeConveyor;
import it.playfellas.superapp.logic.Config2;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.tiles.Tile;
import it.playfellas.superapp.ui.BitmapUtils;
import it.playfellas.superapp.ui.MovingConveyorListener;
import it.playfellas.superapp.ui.slave.SlaveGameFragment;
import it.playfellas.superapp.ui.slave.SlavePresenter;
import lombok.Getter;

/**
 * Created by Stefano Cappa on 30/07/15.
 */
public class SlaveGame2Fragment extends SlaveGameFragment {
    public static final String TAG = SlaveGame2Fragment.class.getSimpleName();

    private static Bitmap photo;

    @Getter
    private SizeConveyor conveyorUp;
    @Getter
    private MovingConveyor conveyorDown;

    protected static Config2 config;
    protected static TileSelector db;
    private Slave2Presenter slave2Presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.slave_game2_fragment;
    }

    @Override
    protected void onCreateView(View root) {
        ButterKnife.bind(this, root);
    }

    @Override
    public void onDestroyView() {
        if (conveyorDown != null) {
            conveyorDown.clear();
            conveyorDown.stop();
        }

        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     * You can't put this method in a superclass because you can't create a static abstract method.
     */
    public static SlaveGame2Fragment newInstance(TileSelector ts, Config2 config2, Bitmap photoBitmap) {
        SlaveGame2Fragment fragment = new SlaveGame2Fragment();
        db = ts;
        config = config2;
        photo = photoBitmap;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.pausePresenter();
    }

    @Override
    public void pausePresenter() {
        if (this.slave2Presenter != null) {
            this.slave2Presenter.pause();
        }
    }

    @Override
    public void restartPresenter() {
        if (this.slave2Presenter != null) {
            this.slave2Presenter.restart();
        }
    }

    @Override
    protected SizeConveyor newConveyorUp() {
        conveyorUp = new SizeConveyor(null);
        return conveyorUp;
    }

    @Override
    protected MovingConveyor newConveyorDown() {
        conveyorDown = new MovingConveyor(new MovingConveyorListener(), 5, MovingConveyor.RIGHT);
        return conveyorDown;
    }

    @Override
    protected SlavePresenter newSlavePresenter() {
        this.slave2Presenter = new Slave2Presenter(db, this, config);
        this.slave2Presenter.startTileDisposer();
        return this.slave2Presenter;
    }

    public void showBaseTiles(Tile[] tiles) {
        conveyorUp.addBaseTiles(tiles);
    }
}

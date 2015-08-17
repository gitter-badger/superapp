package it.playfellas.superapp.ui.slave.game1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.playfellas.superapp.R;
import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.logic.db.TileSelector;
import it.playfellas.superapp.ui.slave.Conveyor;
import it.playfellas.superapp.ui.slave.SlaveGameFragment;
import lombok.Getter;

/**
 * Created by Stefano Cappa on 30/07/15.
 */
public class SlaveGame1Fragment extends SlaveGameFragment {
    public static final String TAG = SlaveGame1Fragment.class.getSimpleName();

    protected Slave1Presenter presenter;

    @Bind(R.id.photoImageView)
    ImageView photoImageView;

    private static Bitmap photo;

    @Getter
    private Conveyor conveyorUp, conveyorDown;

    @Bind(R.id.downConveyor)
    LinearLayout downConveyorLayout;

    @Bind(R.id.upConveyor)
    LinearLayout upConveyorLayout;

    protected static Config1 config;
    protected static TileSelector db;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     * You can't put this method in a superclass because you can't create a static abstract method.
     */
    public static SlaveGame1Fragment newInstance(TileSelector ts, Config1 config1, Bitmap photoBitmap) {
        SlaveGame1Fragment fragment = new SlaveGame1Fragment();
        db = ts;
        config = config1;
        photo = photoBitmap;
        return fragment;
    }

    public SlaveGame1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.slave_game1_fragment, container, false);

        ButterKnife.bind(this, root);

        conveyorUp = new Conveyor(upConveyorLayout, 100, Conveyor.LEFT);
        conveyorDown = new Conveyor(downConveyorLayout, 100, Conveyor.RIGHT);

        conveyorUp.start();
        conveyorDown.start();

        photoImageView.setImageBitmap(photo);

        return root;
    }
}

package it.playfellas.superapp.ui.slave;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.io.IOException;

import butterknife.ButterKnife;
import it.playfellas.superapp.R;
import it.playfellas.superapp.events.EventFactory;
import it.playfellas.superapp.events.bt.BTConnectedEvent;
import it.playfellas.superapp.events.bt.BTDisconnectedEvent;
import it.playfellas.superapp.events.game.BeginStageEvent;
import it.playfellas.superapp.events.game.EndStageEvent;
import it.playfellas.superapp.events.game.StartGame1Color;
import it.playfellas.superapp.events.game.StartGame1Direction;
import it.playfellas.superapp.events.game.StartGame1Shape;
import it.playfellas.superapp.events.game.StartGame2Event;
import it.playfellas.superapp.events.game.StartGame3Event;
import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.logic.Config2;
import it.playfellas.superapp.logic.Config3;
import it.playfellas.superapp.logic.db.DbAccess;
import it.playfellas.superapp.logic.db.DbException;
import it.playfellas.superapp.logic.db.DbFiller;
import it.playfellas.superapp.logic.tiles.TileColor;
import it.playfellas.superapp.logic.tiles.TileDirection;
import it.playfellas.superapp.logic.tiles.TileShape;
import it.playfellas.superapp.network.TenBus;
import it.playfellas.superapp.ui.master.BitmapUtils;
import it.playfellas.superapp.ui.slave.game1.SlaveGame1ColorFragment;
import it.playfellas.superapp.ui.slave.game1.SlaveGame1DirectionFragment;
import it.playfellas.superapp.ui.slave.game1.SlaveGame1Fragment;
import it.playfellas.superapp.ui.slave.game1.SlaveGame1ShapeFragment;
import it.playfellas.superapp.ui.slave.game2.SlaveGame2ColorFragment;
import it.playfellas.superapp.ui.slave.game2.SlaveGame2Fragment;

/**
 * Created by Stefano Cappa on 30/07/15.
 */
public class SlaveActivity extends AppCompatActivity implements
        PhotoFragment.PhotoFragmentListener,
        StartSlaveGameListener {

    private static final String TAG = SlaveActivity.class.getSimpleName();

    private BluetoothAdapter mBluetoothAdapter = null;

    private Bitmap photoBitmap;

    private DbAccess db;

    private SlaveGameFragment currentSlaveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slave);

        ButterKnife.bind(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        TenBus.get().register(this);

        this.listen();

        //the first thing to do is to start the waiting fragment to wait for a connection
        //when the connection will be available this activity receives BTConnectedEvent and replace the fragment
        //with the photoFragment, as in method "onBTConnectedEvent"
        //i pass null to newInstance of WaitingFragment to specify that i want the default behaviour with the standard
        //message. In recallWaitingFragment(String message) i pass a message.
        this.changeFragment(WaitingFragment.newInstance(null), WaitingFragment.TAG);

        this.db = new DbAccess(this);

        //Fill the db
        try {
            (new DbFiller(this.db)).fill();
        } catch (DbException e) {
            Log.e(TAG, "DbException", e);
            finish();
        }
    }

    @Override
    public void setPhotoBitmap(Bitmap photo) {
        Log.d(TAG, "setPhotoBitmap in Slave Activity has photo" + (photo == null ? "==" : "!=") + "null");
        this.photoBitmap = photo;
    }

    @Override
    public void sendPhotoEvent() {
        Log.d(TAG, "sendPhotoEvent in Slave Activity has photoBitmap" + (photoBitmap == null ? "==" : "!=") + "null");
        byte[] photoByteArray = BitmapUtils.toByteArray(photoBitmap);
        Log.d(TAG, "sendPhotoEvent in slaveActivity with photoByteArray.length= " + photoByteArray.length);
        TenBus.get().post(EventFactory.sendPhotoByteArray(photoByteArray));
    }

    @Override
    public void recallWaitingFragment(String message) {
        this.changeFragment(WaitingFragment.newInstance(message), WaitingFragment.TAG);
    }


    private void listen() {
        ensureDiscoverable();
        try {
            TenBus.get().attach(null);
        } catch (IOException e) {
            Toast.makeText(this, "Listen error", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.slave_root_container, fragment, tag);
        executePendingTransactions(fragmentTransaction);
    }

    private void executePendingTransactions(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.commit();
        this.getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    @Subscribe
    public void onBTConnectedEvent(BTConnectedEvent event) {
        Toast.makeText(this, event.getDevice().getName(), Toast.LENGTH_SHORT).show();
        this.changeFragment(PhotoFragment.newInstance(), PhotoFragment.TAG);
    }

    @Subscribe
    public void onBTDisconnectedEvent(BTDisconnectedEvent event) {
        Toast.makeText(this, event.getDevice().getName(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onBTStartGame1ColorEvent(StartGame1Color event) {
        Config1 config = event.getConf();
        TileColor tc = event.getBaseColor();
        this.currentSlaveFragment = SlaveGame1ColorFragment.newInstance(this.db, config, tc, this.photoBitmap);
        this.changeFragment(this.currentSlaveFragment, SlaveGame1ColorFragment.TAG);
    }

    @Subscribe
    public void onBTStartGame1DirectionEvent(StartGame1Direction event) {
        Config1 config = event.getConf();
        TileDirection td = event.getBaseDirection();
        this.currentSlaveFragment = SlaveGame1DirectionFragment.newInstance(this.db, config, td, this.photoBitmap);
        this.changeFragment(this.currentSlaveFragment, SlaveGame1DirectionFragment.TAG);
    }

    @Subscribe
    public void onBTStartGame1ShapeEvent(StartGame1Shape event) {
        Config1 config = event.getConf();
        TileShape ts = event.getBaseShape();
        this.currentSlaveFragment = SlaveGame1ShapeFragment.newInstance(this.db, config, ts, this.photoBitmap);
        this.changeFragment(this.currentSlaveFragment, SlaveGame1ShapeFragment.TAG);
    }


    @Subscribe
    public void onBTStartGame2Event(StartGame2Event event) {
        Config2 config = event.getConf();
        this.currentSlaveFragment = SlaveGame2ColorFragment.newInstance(this.db, config, this.photoBitmap);
        this.changeFragment(this.currentSlaveFragment, SlaveGame2Fragment.TAG);
    }

    @Subscribe
    public void onBTStartGame3Event(StartGame3Event event) {
        Config3 config = event.getConf();
        //this.changeFragment(SlaveGame3Fragment.newInstance(this.db, config, this.photoBitmap), SlaveGame3Fragment.TAG);
    }

    @Subscribe
    public void onBeginStageEvent(BeginStageEvent event) {
        //received a BeginStageEvent. For this reason i must restore the
        //game fragment saved in this.currentSlaveFragment
        this.changeFragment(this.currentSlaveFragment, "currentSlaveFragment_TAG" /* TODO: save also the tag in a variable useful here */);
    }

    @Subscribe
    public void onEndStageEvent(EndStageEvent event) {
        //received an EndStageEvent. For this reason i must swap the
        //fragment with the WaitingFragment
        this.changeFragment(WaitingFragment.newInstance("La partita sta per ricominciare"), WaitingFragment.TAG);
    }


    //TODO *************************************************
    //TODO ONLY FOR TESTING DURING DEVELOPMENT
    @Override
    public void selectSlaveGameFragment(int num) {
    }
    //TODO *************************************************

    /**
     * Method definied in {@link StartSlaveGameListener#startSlaveGame(String)} and
     * called in {@link SlaveGame1Fragment}, {@link SlaveGame2Fragment} and {@link SlaveGame3Fragment}.
     *
     * @param tagFragment
     */
    @Override
    public void startSlaveGame(String tagFragment) {
        //TODO implement this method if you want to add some behaviours when
        //TODO slavefragment startSlaveGame method in StartSlaveGameListener
    }
}

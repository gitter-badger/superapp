package it.playfellas.superapp.network;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.io.IOException;

import it.playfellas.superapp.events.EventFactory;
import it.playfellas.superapp.events.InternalEvent;
import it.playfellas.superapp.events.NetEvent;

/**
 * Wraps Otto Bus to extend it with the ability to post on the main thread
 */
public class TenBus {
    private static final String TAG = TenBus.class.getSimpleName();
    private final Handler mainThread = new Handler(Looper.getMainLooper());
    private static TenBus bus;
    private Bus ottoBus;
    private Peer peer;

    private TenBus(ThreadEnforcer t) {
        ottoBus = new Bus(t);
        peer = null;
    }

    public synchronized static TenBus get() {
        if (bus == null) {
            bus = new TenBus(ThreadEnforcer.ANY);
        }
        return bus;
    }

    public void attach(BluetoothDevice device) throws IOException {
        if (peer == null) {
            // need to instantiate a new peer
            if (device == null) {
                peer = new SlavePeer();
            } else {
                peer = new MasterPeer();
            }
        }
        peer.obtainConnection(device);
    }

    public void register(final Object subscriber) {
        ottoBus.register(subscriber);
    }

    public void detach() {
        if (peer == null) {
            Log.e(TAG, "Cannot detach if not attached!");
            return;
        }

        peer.close();
        peer = null;
    }

    private void logEvent(final Object event) {
        if (Config.DEBUG) {
            Log.d(event.getClass().getSimpleName(), event.toString());
        }
    }

    void postInternal(final Object event) {
        logEvent(event);

        if (Looper.myLooper() == Looper.getMainLooper()) {
            ottoBus.post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    ottoBus.post(event);
                }
            });
        }
    }

    private void postNet(final NetEvent event) throws IOException {
        logEvent(event);

        if (peer == null) {
            Log.e(TAG, "Cannot send NetEvent if no device is attached!");
            throw new IOException();
        }
        peer.sendMessage(event);
    }

    public void post(final InternalEvent e) {
        postInternal(e);
    }

    public void post(final NetEvent e) {
        try {
            postNet(e);
        } catch (IOException ex) {
            String msg = "IO error on posting event " + e.toString();
            Log.e(TAG, msg);
            postInternal(EventFactory.btError(null, msg));
        }
    }
}



package it.playfellas.superapp.events;

import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;

import it.playfellas.superapp.events.bt.BTConnectedEvent;
import it.playfellas.superapp.events.bt.BTConnectingEvent;
import it.playfellas.superapp.events.bt.BTDisconnectedEvent;
import it.playfellas.superapp.events.bt.BTErrorEvent;
import it.playfellas.superapp.events.bt.BTListeningEvent;
import it.playfellas.superapp.events.game.BeginStageEvent;
import it.playfellas.superapp.events.game.EndGameEvent;
import it.playfellas.superapp.events.game.EndStageEvent;
import it.playfellas.superapp.events.game.RTTUpdateEvent;
import it.playfellas.superapp.events.game.RWEvent;
import it.playfellas.superapp.events.game.StartGame1Event;
import it.playfellas.superapp.events.game.StartGame2Event;
import it.playfellas.superapp.events.game.StartGame3Event;
import it.playfellas.superapp.events.game.ToggleGameModeEvent;
import it.playfellas.superapp.events.tile.ClickedTileEvent;
import it.playfellas.superapp.events.tile.NewTileEvent;
import it.playfellas.superapp.logic.tiles.Tile;
import it.playfellas.superapp.logic.Config1;
import it.playfellas.superapp.logic.Config2;
import it.playfellas.superapp.logic.Config3;

public class EventFactory {
    public static StringNetEvent stringEvent(String body) {
        return new StringNetEvent(body);
    }

    public static BTDisconnectedEvent btDisconnected(BluetoothDevice device) {
        return new BTDisconnectedEvent(device);
    }

    public static BTConnectedEvent btConnected(BluetoothDevice device) {
        return new BTConnectedEvent(device);
    }

    public static BTListeningEvent btListening(BluetoothDevice device) {
        return new BTListeningEvent(device);
    }

    public static BTConnectingEvent btConnecting(BluetoothDevice device) {
        return new BTConnectingEvent(device);
    }

    public static BTErrorEvent btError(BluetoothDevice device, String msg) {
        return new BTErrorEvent(device, msg);
    }

    public static NewTileEvent newTile(Tile t) {
        return new NewTileEvent(t);
    }

    public static ClickedTileEvent clickedTile(Tile t) {
        return new ClickedTileEvent(t);
    }

    public static ToggleGameModeEvent gameChange() {
        return new ToggleGameModeEvent();
    }

    public static RWEvent rw(boolean right) {
        return new RWEvent(right);
    }

    public static RTTUpdateEvent rttUpdate(float rtt) {
        return new RTTUpdateEvent(rtt);
    }

    public static BeginStageEvent beginStage() {
        return new BeginStageEvent();
    }

    public static EndStageEvent endStage() {
        return new EndStageEvent();
    }

    public static PhotoEvent sendPhoto(Bitmap b) {
        return new PhotoEvent(b);
    }

    public static StartGame1Event startGame1(Config1 conf) {
        return new StartGame1Event(conf);
    }

    public static StartGame2Event startGame2(Config2 conf) {
        return new StartGame2Event(conf);
    }

    public static StartGame3Event startGame3(Config3 conf) {
        return new StartGame3Event(conf);
    }

    public static EndGameEvent endGame() {
        return new EndGameEvent();
    }
}


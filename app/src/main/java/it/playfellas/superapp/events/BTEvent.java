package it.playfellas.superapp.events;

import android.bluetooth.BluetoothDevice;

public abstract class BTEvent extends InternalEvent {
  private BluetoothDevice device;
  protected String message = "BTEvent";

  public BTEvent(BluetoothDevice device) {
    super();
    this.device = device;
  }

  @Override public String toString() {
    String prefix = "me: ";
    if (device != null) {
      prefix = device.getName() + ": ";
    }
    return prefix + message;
  }

  public BluetoothDevice getDevice() {
    return device;
  }
}

package it.playfellas.superapp;

/**
 * Created by Stefano Cappa on 04/08/15.
 */
public class InternalConfig {
    //************DB************
    public static final String DATABASE_NAME = "superapp.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tiles";

    public static final String KEY_ID = "_id";
    public static final String KEY_URL = "url";
    public static final String KEY_COLOR = "color";
    public static final String KEY_SHAPE = "shape";
    public static final String KEY_DIRECTION = "direction";
    public static final String KEY_TYPE = "type";
    public static final String KEY_SIZE = "size";

    public static final String[] ALL_COLUMNS = new String[]{
            InternalConfig.KEY_URL, InternalConfig.KEY_COLOR, InternalConfig.KEY_SHAPE,
            InternalConfig.KEY_DIRECTION, InternalConfig.KEY_TYPE, InternalConfig.KEY_SIZE};

    //************BT************
    public static final boolean BT_DEBUG = true;

    public static final String BT_APP_NAME_SECURE = "SuperApp";
    public static final String BT_MY_SALT_SECURE = "fa87c0d0-afac-11de-8a39-";

    //************INTRUDER************
    public static final int NO_CRITICAL = 3;
    public static final int NO_EASY = 4;
    public static final int NO_TARGET = 5;
    public static final int PROB_TARGET = 75;
    public static final int PROB_EASY = 20;

    //************RTT_LOGIC************
    public static final int DECREASE_STEPS = 5;
    public static final int RTT_UPDATE_PERIOD = 10;
    public static final float BASE_MAX_RTT = 5;
    public static final float BASE_MIN_RTT = 2;
}
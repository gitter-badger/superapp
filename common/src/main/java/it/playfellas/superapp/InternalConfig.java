package it.playfellas.superapp;

import it.playfellas.superapp.tiles.TileDirection;

/**
 * Created by Stefano Cappa on 04/08/15.
 */
public class InternalConfig {
    //************DB************
    public static final String DATABASE_NAME = "superapp.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tiles";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_COLOR = "color";
    public static final String KEY_SHAPE = "shape";
    public static final String KEY_DIRECTABLE = "direction";
    public static final String KEY_TYPE = "type";

    public static final String[] ALL_COLUMNS = new String[]{
            InternalConfig.KEY_ID, InternalConfig.KEY_NAME, InternalConfig.KEY_COLOR, InternalConfig.KEY_SHAPE,
            InternalConfig.KEY_DIRECTABLE, InternalConfig.KEY_TYPE};
    public static final String DRAWABLE_RESOURCE = "drawable";
    public static final String PACKAGE_NAME = "it.playfellas.superapp";

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
    public static final TileDirection DEFAULT_DIRECTION = TileDirection.LEFT;

    //************RTT_LOGIC************
    public static final int DECREASE_STEPS = 5;
    public static final int RTT_UPDATE_PERIOD = 10;
    public static final float BASE_MAX_RTT = 10;
    public static final float BASE_MIN_RTT = 5;

    //************GAME23************
    public static final int NO_FIXED_TILES = 4;
    // "Le forme da riordinare sono in rapporto 1:5 con i distrattori" (Cit. Specifiche)
    // mmmm... Seems not so legit to me
    public static final double GAME23_TGT_PROB = 0.6;
    public static final int GAME3_NO_DISTRACTORS = 7;

    //*******DIALOG FRAGMENTS*******
    public static final String ENDSTAGE_DIAG_TAG = "endStageDialogFragment";
    public static final String MASTER_DIAG_TAG = "masterDialogFragment";
    public static final String ENDTURN_DIAG_TAG = "endTurnDialogFragment";
    public static final String MASTER_AREYOUSURE_DIAG_TAG = "areYourSureDialogFragment";
    public static final int ENDSTAGE_DIAG_ID = 1;
    public static final int MASTER_DIAG_ID = 2;
    public static final int ENDTURN_DIAG_ID = 3;
    public static final int MASTER_AREYOUSURE_DIAG_ID = 4;
}

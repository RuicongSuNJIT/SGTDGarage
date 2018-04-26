package com.sg.tdgarage.configure;

import com.sg.tdgarage.structure.TimeSpot;

public class Constant {
    public static final Integer ON_RUNNING = 1;
    public static final Integer ON_FREE = 0;
    public static final Integer LOT_FREE = 0;
    public static final Integer LOT_USED = 1;
    public static final Double MID = 3.5;
    public static final int LIFTER_SPOT_NO = 0;
    public static final double SHUTTLE_ACC_TIME = 2;
    public static final double SHUTTLE_SPD_WEIGHTED = 0.5;
    public static final double SHUTTLE_SPD_EMPTY = 1;
    public static final double BUILDING_INTERVAL = 0.652;
    public static final double DISTANCE_OF_LIFTER = 22.86;
    public static final String TIME_SEQ_FILE_NAME = "Time.xlsx";
    public static final double COMBINE_LOADING_TIME_TOTAL = 52.444;
    public static final TimeSpot INIT_ZERO_TIME = new TimeSpot(0,0,0);
    public static final String LOG_FILE_NAME = "src/main/resources/log.txt";
}

package com.sg.tdgarage.configure;

/**
 * 升降机运行时间数组
 */
public class LiftingTime {
    /**
     * liftingTimes[i]表示，升降机从1层升至i层所需时间，亦是从i层降至1层所需时间
     */
    private int[] liftingTimes = new int[]{0, 0, 16, 29, 43};

    /**
     * 获得升降机运行时间
     * @param floor 升降机将从1层上升到的楼层，或者将从此楼层下降至1层
     */
    public int getLiftingTime(int floor) {
        return liftingTimes[floor];
    }
}

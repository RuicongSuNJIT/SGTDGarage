package com.sg.tdgarage.structure;

import com.sg.tdgarage.configure.LiftingTime;
import com.sg.tdgarage.configure.Constant;

public class Lifter {
//    //状态
//    private Integer state;

    /**
     * 升降机状态
     */
    private Status status;

    private static LiftingTime liftingTime = new LiftingTime();

    private TimeSpot end;

    //当前行为要花费的时间
    private Double currentCostTime;
    //楼号
    private Integer buildNum;

//    public Lifter(){
//        this.state = Constant.ON_FREE;
//    }
//
//    public Integer getState() {
//        return state;
//    }
//
//    public Lifter setState(Integer state) {
//        this.state = state;
//        return this;
//    }

    public Lifter() {
        this.status = Status.STOP;
        this.end = Constant.INIT_ZERO_TIME;
    }

    public Double getCurrentCostTime() {
        return currentCostTime;
    }

    public Lifter setCurrentCostTime(Double currentCostTime) {
        this.currentCostTime = currentCostTime;
        return this;
    }

    public Integer getBuildNum() {
        return buildNum;
    }

    public Lifter setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
        return this;
    }

    public int getLiftingTIme(int floor) {
        return liftingTime.getLiftingTime(floor);
    }

    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    public TimeSpot getEnd() {
        return this.end;
    }

    public void setEnd(TimeSpot end) {
        this.end = end;
    }

    public enum Status {
        RUNNING, STOP
    }

    @Override
    public String toString() {
        return "[Lifter@" + Integer.toHexString(hashCode()) + "]";
    }
}

package com.sg.tdgarage.structure;

import com.sg.tdgarage.configure.Constant;
import com.sg.tdgarage.event.EventRecorder;
import com.sg.tdgarage.event.MessageEvent;

public class Shuttle {
    //    //状态
//    private Integer state;
//    //梭车相对楼层的位置
//    private Position position;

    /**
     * 穿梭车当前状态
     */
    private Status status;

    /**
     * 穿梭车当前位置
     */
    private PSPosition position;

    /**
     * 所属车库数组
     */
    private Building[] owners;

    /**
     * 当前所在车库
     */
    private Building currentBuilding;

//    //楼号
//    private Integer buildingNum;

    //层号
    private Integer floorNum;
    //当前行为要花费的时间
    private Double currentCostTime;

    /**
     * 上一次运行结束的时间
     */
    private TimeSpot end;

    public Shuttle(Building[] owners, int floor, int[][] scanSequence) {
        this.status = Status.STOP;
        this.position = new PSPosition(floor, Constant.LIFTER_SPOT_NO);
        this.owners = owners;
        this.currentBuilding = owners[0];
        owners[0].setFloorScanSequence(scanSequence[0]);
        owners[1].setFloorScanSequence(scanSequence[1]);
        this.end = Constant.INIT_ZERO_TIME;
    }

//    public HorizontalShuttle(int buildingNum){
//        this.state = Constant.ON_FREE;
//        this.position = new Position(Constant.MID,0);
//        this.buildingNum = buildingNum;
//    }
//    public Integer getState() {
//        return state;
//    }
//
//    public HorizontalShuttle setState(Integer state) {
//        this.state = state;
//        return this;
//    }

//    public Position getPosition() {
//        return position;
//    }
//
//    public HorizontalShuttle setPosition(Position position) {
//        this.position = position;
//        return this;
//    }

//    public Integer getBuildingNum() {
//        return buildingNum;
//    }
//
//    public Shuttle setBuildingNum(Integer buildingNum) {
//        this.buildingNum = buildingNum;
//        return this;
//    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public Shuttle setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
        return this;
    }

    public Double getCurrentCostTime() {
        return currentCostTime;
    }

    public Shuttle setCurrentCostTime(Double currentCostTime) {
        this.currentCostTime = currentCostTime;
        return this;
    }

    public void leaveBuilding(Building oldBuilding) {
        Building newBuilding;

        // 修改穿梭机当前所属库
        if (owners[0] == oldBuilding) {
            newBuilding = owners[1];
        } else {
            newBuilding = owners[0];
        }

        setCurrentBuilding(newBuilding);

        EventRecorder.record(new MessageEvent(end, oldBuilding, this, "前往 " + newBuilding));

        // 穿梭车从当前库的升降机位置移动到另一个库的升降机位置所用的时间。
        int movingTime = (int) Math.ceil(Constant.DISTANCE_OF_LIFTER / Constant.SHUTTLE_SPD_EMPTY +
                Constant.SHUTTLE_ACC_TIME);

        // 穿梭车到达另一个车库的时间点, 这属于一次新的运行，需要更新其运行停止时间
        end = end.add(movingTime);
        EventRecorder.record(new MessageEvent(end, newBuilding, this, "到达 " + newBuilding));
    }

    public TimeSpot getEnd() {
        return end;
    }

    public void setEnd(TimeSpot end) {
        this.end = end;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    private enum Status {
        RUNNING, STOP
    }

    @Override
    public String toString() {

        return "[Shuttle[Floor " + position.getFloor() + "]]";
    }
}

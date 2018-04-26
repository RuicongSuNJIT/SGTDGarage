package com.sg.tdgarage.structure;

import java.util.Comparator;

public class Bus {
    //停车位号
    private Integer lotNum;
    //层号
    private Integer floorNum;
    //楼号
    private Integer buildingNum;
    //出车所需时间
    private Double leaveCostTime;

    /**
     * 所属线路
     */
    private BusLine busLine;

    /**
     * 发车时间
     */
    private TimeSpot departureTime;
//    //线路号
//    private Integer busLine;
//    public Integer getBusLine() {
//        return busLine;
//    }
//
//    public Bus setBusLine(Integer busLine) {
//        this.busLine = busLine;
//        return this;
//    }

    public Bus(BusLine busLine, TimeSpot departureTime) {
        this.departureTime = departureTime;
        this.busLine = busLine;
    }

    public Integer getLotNum() {
        return lotNum;
    }

    public Bus setLotNum(Integer lotNum) {
        this.lotNum = lotNum;
        return this;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public Bus setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
        return this;
    }

    public Integer getBuildingNum() {
        return buildingNum;
    }

    public Bus setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
        return this;
    }

    public Double getLeaveCostTime() {
        return leaveCostTime;
    }

    public Bus setLeaveCostTime(Double leaveCostTime) {
        this.leaveCostTime = leaveCostTime;
        return this;
    }

    /**
     * 优先级比较，跟据发车时间和线路优先级，进行比较
     * 用于排序
     */
    public static Comparator<Bus> comp = (bus1, bus2) -> {
        int interval = bus1.departureTime.interval(bus2.departureTime);
        if (interval == 0) {
            double diffPriority = bus1.busLine.getPriority() - bus2.busLine.getPriority();
            if (diffPriority > 0) {
                return -1;
            } else if (diffPriority < 0) {
                return 1;
            }
        }
        return interval;
    };

    public TimeSpot getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(TimeSpot departureTime) {
        this.departureTime = departureTime;
    }

    public String getLineName() {
        return busLine.getName();
    }

    @Override
    public String toString() {
        return "[Bus[Line #" + busLine.getName() + ", Leave @" + departureTime + "]]";
    }
}

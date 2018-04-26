package com.sg.tdgarage.structure;

import com.sg.tdgarage.configure.Constant;

public class Combiner {
    //状态
    private Integer state;
    //当前行为要花费的时间
    private Double currentCostTime;
    //楼号
    private Integer buildingNum;
    //层号
    private Integer floorNum;

    public Combiner(){
        this.state = Constant.ON_FREE;
    }
    public Integer getState() {
        return state;
    }

    public Combiner setState(Integer state) {
        this.state = state;
        return this;
    }

    public Double getCurrentCostTime() {
        return currentCostTime;
    }

    public Combiner setCurrentCostTime(Double currentCostTime) {
        this.currentCostTime = currentCostTime;
        return this;
    }

    public Integer getBuildingNum() {
        return buildingNum;
    }

    public Combiner setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
        return this;
    }

    public Integer getFloorNum() {
        return floorNum;
    }

    public Combiner setFloorNum(Integer floorNum) {
        this.floorNum = floorNum;
        return this;
    }

}


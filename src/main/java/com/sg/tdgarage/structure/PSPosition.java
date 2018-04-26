package com.sg.tdgarage.structure;

/**
 * 单库内，相对位置。
 */
public class PSPosition {
    /**
     * 车位所属楼层: 2, 3, 4
     */
    private int floor;
    /**
     * 车位编号: 1, 2, 3...
     */
    private int spotNo;

    public PSPosition(int floor, int spotNo) {
        this.floor = floor;
        this.spotNo = spotNo;
    }

    public int getFloor() {
        return floor;
    }

    public PSPosition setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public int getSpotNo() {
        return spotNo;
    }

    public PSPosition setSpotNo(int spotNo) {
        this.spotNo = spotNo;
        return this;
    }
}
//
//public class Position {
//    //x轴位置
//    private Double x;
//    //y轴位置
//    private Double y;
//    //起始点为每层1号车位
//    public Position(double x, double y){
//        this.x = x;
//        this.y = y;
//    }
//
//    public double getX() {
//        return x;
//    }
//
//    public Position setX(double x) {
//        this.x = x;
//        return this;
//    }
//
//    public double getY() {
//        return y;
//    }
//
//    public Position setY(double y) {
//        this.y = y;
//        return this;
//    }
//
//
//
//}

package com.sg.tdgarage.configure;

/**
 * 车库规格配置
 */
public class BuildingConfiguration {
    /**
     * 车库层数
     */
    public int floors = 4;
    /**
     * 每层车位数
     */
    public int parkingSpotPerFloor = 10;
    /**
     * 构建距离矩阵
     */
    public DistanceMatrix distanceMatrix = new DistanceMatrix();
    /**
     * 一层无车位
     */
    public boolean firstLevelEmpty = true;
}

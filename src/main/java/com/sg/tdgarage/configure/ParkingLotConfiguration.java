package com.sg.tdgarage.configure;

/**
 * 整合所有的Configuration。
 * 注意：所有的序号均从1开始。比如1层就是floor=1，1号车库就是building.id = 1。而非传统的从0开始。
 */
public class ParkingLotConfiguration {
    /**
     * 设置车库有几栋楼。本算法目前只支持偶数栋楼。因为算法会两两一对的使用车库。
     */
    public int buildings = 4;
    public BuildingConfiguration[] buildingConfigs;
    public ShuttleConfiguration[] shuttleConfigs;

    /**
     * 调度算法关键策略。
     * 也是对称的体现之一，shuttle一段时间只会在一个车库内运作，直到将此车库的车位分配完毕。
     * 一对车库，在相近的同一段时间内，不会同时使用同一层的shuttle
     */
    public int[][] scanSequence;

    public ParkingLotConfiguration() {
        // 为每栋车库提供一个配置对象，目前都使用的默认配置。可以自行修改，但是需要每一对相同规格。
        buildingConfigs = new BuildingConfiguration[buildings + 1];
        for (int i = 1; i <= buildings; ++i) {
            buildingConfigs[i] = new BuildingConfiguration();
        }

        // 配置穿梭车关联关系。
        shuttleConfigs = new ShuttleConfiguration[2];
        shuttleConfigs[0] = new ShuttleConfiguration(new int[]{1, 2});
        shuttleConfigs[1] = new ShuttleConfiguration(new int[]{3, 4});


        // 数组[0]表示一对车库的基数编号的那个，数组[1]表示一对车库偶数编号的那个
        //      building[0]  building[1]
        // seq 1    4           3
        // seq 2    2           4
        // seq 3    3           2
        // 以上配置表示，在三个时间段，1，2，3
        // 每个时间段，一对车库使用scanSequence[building][seq]层的shuttle。
        // 此配置需要保证，上表所示，每行不同
        // 此配置目前为了简化工作，以人工设置，此思想可以转换为算法实现。
        this.scanSequence = new int[][]{{4, 2, 3}, {3, 4, 2}};
    }
}

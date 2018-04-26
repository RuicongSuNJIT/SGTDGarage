package com.sg.tdgarage.structure;

import com.sg.tdgarage.configure.ParkingLotConfiguration;
import com.sg.tdgarage.configure.ShuttleConfiguration;
import com.sg.tdgarage.configure.Constant;
import com.sg.tdgarage.event.EventRecorder;
import com.sg.tdgarage.event.MessageEvent;

import java.util.LinkedList;
import java.util.List;

public class ParkingLot {
    private Building[] buildings;
    private int buildingNum;
    private List<Shuttle> shuttleList;
    private int roundIdx = 1;

    public ParkingLot(ParkingLotConfiguration config) {
        this.buildings = new Building[config.buildings + 1];
        this.buildingNum = config.buildings;
        this.shuttleList = new LinkedList<>();

        for (int buildingNo = 1; buildingNo <= config.buildings; ++buildingNo) {
            buildings[buildingNo] = new Building(buildingNo, config.buildingConfigs[buildingNo]);
        }

        for (ShuttleConfiguration shuttleConfig : config.shuttleConfigs) {
            int floors = config.buildingConfigs[shuttleConfig.owners[0]].floors;
            int[] ownerNos = shuttleConfig.owners;
            for (int floor = 2; floor <= floors; ++floor) {
                Building[] owners = new Building[]{buildings[ownerNos[0]], buildings[ownerNos[1]]};
                Shuttle shuttle = new Shuttle(owners, floor, config.scanSequence);
                for (Building owner : owners) {
                    owner.setShuttle(floor, shuttle);
                }
                shuttleList.add(shuttle);
            }
        }
    }

    public Building[] getBuildings() {
        return buildings;
    }

    private Building nextBuilding() {
        Building building = buildings[roundIdx];
        ++roundIdx;
        if (roundIdx > buildingNum) {
            roundIdx = 1;
        }
        return building;
    }

    /**
     * 车位分配算法
     *
     * @param departureBus
     * @param bus 要分配车位的车
     * @param time 当前时间点（公交车出发的时间）
     * @return 返回操作的车库 （用于打印记录）
     */

    public Building allocateParkingSpot(Bus departureBus, Bus bus, TimeSpot time) {
        Building building = nextBuilding();
        ParkingSpot spot = building.getParkingSpot();
        Shuttle shuttle = building.getShuttle();
        Lifter lifter = building.getLifter();
        int floor = spot.getFloor();

        if (building != shuttle.getCurrentBuilding()) {
            System.out.println("Shuttle Problem.");
        }

        // 升降机升至指定楼层的时间点
        int liftingTime = lifter.getLiftingTIme(floor);

        // 计算升降机能到达指定楼层的最早时间点  （上次结束 + 上升时间）
        TimeSpot lifterEnd = lifter.getEnd();
        TimeSpot lifterArriveFloor = lifterEnd.add(liftingTime);

        // 穿梭车移动的距离
        double distance = building.getDistance(spot.getSpotNo());

        // 穿梭车空载移动至车位所用时间  （保留消暑位）
        double shuttleToSpotTime =
                distance / Constant.SHUTTLE_SPD_EMPTY + Constant.SHUTTLE_ACC_TIME;

        // 穿梭车负载移动至升降机所用时间  （保留小数位）
        double shuttleToLifterTime =
                distance / Constant.SHUTTLE_SPD_WEIGHTED + Constant.SHUTTLE_ACC_TIME;

        // 梳齿车所用时间  （保留小数位）
        double combinerLoadingTime = Constant.COMBINE_LOADING_TIME_TOTAL;

        // 穿梭车完成移至车位，载入车辆，移至升降机所需要的总时间 （向上取整）
        int shuttleTotalTime = (int) Math
                .ceil(shuttleToSpotTime + combinerLoadingTime + shuttleToLifterTime);

        // 穿梭车完成本次加载车辆的最早时间点  （上次结束 + 加载时间）
        TimeSpot shuttleEnd = shuttle.getEnd();
        TimeSpot shuttleArriveLifter = shuttleEnd.add(shuttleTotalTime);

        // 比较升降机到达时间和穿梭车到达时间，取较晚的一个
        if (shuttleArriveLifter.isAfter(lifterArriveFloor)) {
            lifterArriveFloor = shuttleArriveLifter;
        }

        // 梳齿车完成将公交车移入升降机的时间点  （穿梭车完成全部工作）
        TimeSpot lifterLoadedBus = lifterArriveFloor.add((int) Math.ceil(combinerLoadingTime));

        // 升降机到达地面的时间点
        TimeSpot lifterArriveGround = lifterLoadedBus.add(liftingTime);

        // 升降机真正到达地面的时间
        TimeSpot realArriveGround;
        // 对比公交车出库完成时间 与 缓冲区产生空位时间（当前发车时间）
        if (lifterArriveGround.isAfter(time)) {
            // 如果 车出库完成 晚于 缓冲区产生空位，则应以出库完成时间作为真正到地时间
            realArriveGround = lifterArriveGround;
        } else {
            // 如果 车出库完成 早于 缓冲区产生空位，则应以缓冲区产生空位的时间作为真正到地时间。
            realArriveGround = time;
        }

        EventRecorder.record(new MessageEvent(realArriveGround, building, lifter, "升降机到达地面"));

        // 升降机完成卸载到地的公交车的时间点  （升降机完成全部工作）
        TimeSpot lifterUnloadedBus = realArriveGround.add((int) Math.ceil(combinerLoadingTime));

        EventRecorder.record(new MessageEvent(lifterUnloadedBus, building, bus, "公交车完成出库"));

        // 如果所有工作完成的时间点 晚于 出库车辆的预计发车时间，打印Late
        if (lifterUnloadedBus.isAfter(bus.getDepartureTime())) {
            bus.setDepartureTime(lifterUnloadedBus);
            System.out.println("Late!");
        }

        // 设定新的升降机结束时间 （完成到地卸载的时间点）
        lifter.setEnd(lifterUnloadedBus);

        // 根据真正到地时间，反推真正的完成穿梭车加载车辆到升降机的时间点。
        lifterLoadedBus = realArriveGround.minus(liftingTime);
        shuttle.setEnd(lifterLoadedBus);

        EventRecorder.record(new MessageEvent(lifterLoadedBus, building, lifter, "升降机开始下降"));

        EventRecorder.record(new MessageEvent(lifterLoadedBus, building, shuttle, "梳齿车完成卸载"));

        lifterArriveFloor = lifterLoadedBus.minus((int) Math.ceil(combinerLoadingTime));

        EventRecorder.record(new MessageEvent(lifterArriveFloor, building, shuttle,
                "穿梭车到达升降机出, 梳齿车开始卸载"));

        EventRecorder.record(new MessageEvent(lifterArriveFloor, building, lifter,
                "升降机到达 " + floor + " 楼"));

        TimeSpot lifterStart = lifterArriveFloor.minus(liftingTime);
        EventRecorder.record(new MessageEvent(lifterStart, building, lifter, "升降机开始上升"));

        TimeSpot shuttleStart = lifterArriveFloor.minus(shuttleTotalTime);
        EventRecorder
                .record(new MessageEvent(shuttleStart, building, shuttle, "穿梭车开始寻车, 装载并移至升降机"));

        spot.setUsed(bus);

        EventRecorder
                .record(new MessageEvent(Constant.INIT_ZERO_TIME, building, spot, "分配 " + bus));

        return building;
    }
}

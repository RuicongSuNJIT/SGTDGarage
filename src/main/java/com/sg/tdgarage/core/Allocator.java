package com.sg.tdgarage.core;

import com.sg.tdgarage.event.DepartureEvent;
import com.sg.tdgarage.event.EventDriver;
import com.sg.tdgarage.event.EventRecorder;
import com.sg.tdgarage.event.MessageEvent;
import com.sg.tdgarage.structure.*;

import java.util.List;
import java.util.Queue;

public class Allocator {
    private final ParkingLot lot;
    private final Queue<Bus> busQueue;
    private final List<BusLine> lineList;

    public Allocator(ParkingLot lot, Queue<Bus> busQueue, List<BusLine> lineList) {
        this.lot = lot;
        this.busQueue = busQueue;
        this.lineList = lineList;
    }

    /**
     * 每当一辆车发车，选择发车队列的下一辆车，
     * 为它分配一个车位，生成它的发车事件，并将此事件加入队列。
     *
     * @param departureBus 处理的发车事件所代表的将要出发的车
     * @param time 发车时间
     */
    public void schedule(Bus departureBus, TimeSpot time) {
        // 如果发车队列以空，即出库完毕。则直接返回。
        if (busQueue.isEmpty()) {
            EventRecorder.record(new MessageEvent(time, null, departureBus, "发车"));
            return;
        }
        // 选择即将出发的下一辆车，
        Bus bus = busQueue.poll();
        // 为它分配一个车位，尽量保证它准发发车
        Building building = lot.allocateParkingSpot(departureBus, bus, time);
        EventRecorder.record(new MessageEvent(time, building, departureBus, "发车"));
        // 生成它的发车事件，并加入队列。
        EventDriver.newEvent(new DepartureEvent(bus, this));
    }

    /**
     * 最早发车时间前需要预先出库一部分车辆到缓冲区
     * 按照scanSequence所配置的顺序，对称的使用每一对车库。
     * 使用每层离升降机最远的四个车位作为预先出库车辆的分配位置
     * 为了简化操作尽快展示，此部分代码多为常数，后续可改为配置参数，配置预先出库算法。
     */
    public void prepare() {
        Building[] buildings = lot.getBuildings();
        int len = buildings.length;
        for (int i = 1; i < len; i += 2) {
            // 针对目前的scanSequence，每一对车库的第一时间块所使用的shuttle
            // 基数车库使用的是4层的shuttle
            // 偶数车库使用的是3层的shuttle
            // 为了简化代码，目前数值使用的常数直接写入的方式。后续可以改为参数传入
            // 配置预先出库算法。
            buildings[i].useParkingSpot(4, 1);
            buildings[i].useParkingSpot(4, 10);
            buildings[i + 1].useParkingSpot(3, 1);
            buildings[i + 1].useParkingSpot(3, 1);
        }

        // 根据上面配置的车位数，根据发车顺序，选出相同数量的若干辆车，
        // 并产生发车事件，作为事件驱动的初始状态。
        for (int i = 0; i < 8; ++i) {
            Bus bus = busQueue.poll();
            EventDriver.newEvent(new DepartureEvent(bus, this));
        }

        // 设置偶数楼shuttle归属。
        buildings[2].moveShuttleHere(3);
        buildings[4].moveShuttleHere(3);
    }
}

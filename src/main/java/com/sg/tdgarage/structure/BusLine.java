package com.sg.tdgarage.structure;

import java.util.Comparator;
import java.util.List;

public class BusLine {
    /**
     * 线路民称
     */
    String name;
    /**
     * 线路优先级
     */
    double priority;
    /**
     * 线路车次
     */
    List<Bus> buses;

    private int nextIndex = 0;

    boolean done = false;

    public BusLine(String name) {
        this.name = name;
    }

    /**
     * 排序器。优先级数值越高，越靠前。
     */
    public static Comparator<BusLine> comp = (busLine1, busLine2) -> {
        if (busLine1.priority > busLine2.priority) {
            return -1;
        } else if (busLine1.priority < busLine2.priority) {
            return 1;
        } else {
            return 0;
        }
    };

    @Override
    public String toString() {
        return "BusLine: " + name + " | " + priority;
    }

    public Bus getNextBsus() {
        if (done) {
            return null;
        }
        try {
            Bus bus = buses.get(nextIndex);
            ++nextIndex;
            return bus;
        } catch (IndexOutOfBoundsException e) {
            done = true;
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }
}

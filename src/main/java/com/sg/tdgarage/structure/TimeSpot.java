package com.sg.tdgarage.structure;

import java.util.Calendar;

public class TimeSpot {
    private final int h;
    private final int m;
    private final int s;
    private final int secs;

    /**
     * 将表示时间的字符串转换成时间点对象
     *
     * @param time 表示时间的字符串，格式要求 - h:mm:ss
     * @return 如果成功，返回解析结果；如果失败，则返回null。
     */

    public static TimeSpot parseTimeSpot(String time) {
        String[] timeSplit = time.split(":");
        int h, m, s;
        if (timeSplit.length != 3) {
            return null;
        }
        try {
            h = Integer.parseInt(timeSplit[0]);
        } catch (NumberFormatException e) {
            return null;
        }
        try {
            m = Integer.parseInt(timeSplit[1]);
            if (m < 0 || m > 59) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        try {
            s = Integer.parseInt(timeSplit[2]);
            if (s < 0 || s > 59) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return new TimeSpot(h, m, s);
    }

    /**
     * 根据给定的时分秒数值构造时间点对象。
     *
     * @param h
     * @param m
     * @param s
     */
    public TimeSpot(int h, int m, int s) {
        this.h = h;
        this.m = m;
        this.s = s;
        this.secs = h * 3600 + m * 60 + s;
    }

    private TimeSpot(int secs) {
        this.secs = secs;
        int m = secs / 60;
        this.s = secs % 60;
        this.h = m / 60;
        this.m = m % 60;

    }

    public TimeSpot(Calendar calendar) {
        this(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    /**
     * 获取若干秒之后的时间点。
     *
     * @param secs
     * @return
     */
    public TimeSpot add(int secs) {
        return new TimeSpot(this.secs + secs);
    }

    /**
     * 获取若干秒之前的时间点。
     *
     * @param secs
     * @return
     */
    public TimeSpot minus(int secs) {
        return new TimeSpot(this.secs - secs);
    }

    /**
     * 获得两个时间的差值，单位：s。
     *
     * @param time
     * @return
     */
    public int interval(TimeSpot time) {
        return this.secs - time.secs;
    }

    public boolean isAfter(TimeSpot time) {
        return this.secs > time.secs;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(7);
        sb.append(h).append(":");
        if (m < 10) {
            sb.append("0");
        }
        sb.append(m).append(":");
        if (s < 10) {
            sb.append("0");
        }
        sb.append(s);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeSpot) {
            return this.secs == ((TimeSpot) obj).secs;
        }
        return false;
    }

    public int getSecs() {
        return secs;
    }
}

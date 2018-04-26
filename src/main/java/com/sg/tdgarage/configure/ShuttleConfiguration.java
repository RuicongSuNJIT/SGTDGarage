package com.sg.tdgarage.configure;

/**
 * 穿梭车配置
 * 所有序号均从1开始，而非常用的0。
 */
public class ShuttleConfiguration {
    /**
     * 穿梭车所属的楼号。数组
     */
    public int[] owners;

    public ShuttleConfiguration(int[] owners) {
        this.owners = owners;
    }
}

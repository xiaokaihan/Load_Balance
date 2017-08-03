package com.key.loadbalance.impl;

import com.key.loadbalance.common.IpMap;

import java.util.*;

/**
 * 随机法. 通过系统随机函数，根据后端服务器列表的大小来随机选择其中一台进行访问；
 * 由概率统计理论可以得知，随着调用量的增大，其实实际效果越来越接近平均分配
 * 流量到每一台后端服务器，也就是轮询的效果；
 * Created by Key.Xiao on 2017/8/3.
 */
public class RandomImpl {

    public static String getServer() {
        // 重建一个Map，避免服务器的上下线导致的并发问题
        Map<String, Integer> serverMap = new HashMap<>();
        serverMap.putAll(IpMap.serverWeightMap);

        //取得Ip地址List
        Set<String> ipSet = serverMap.keySet();
        List<String> ipList = new ArrayList<>();
        ipList.addAll(ipSet);

        Random random = new Random();
        int randomPos = random.nextInt(ipList.size()); // 不包括指定数字

        return ipList.get(randomPos);
    }

}

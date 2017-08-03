package com.key.loadbalance.impl;

import com.key.loadbalance.common.IpMap;

import java.util.*;

/**
 * 加权随机法, 与加权轮询法类似，加权随机法也是根据后端服务器不同的配置和负载情况来配置不同的权重。
 * 不同的是，它是按照权重来随机选择服务器的，而不是顺序；
 * <p>
 * Created by Key.Xiao on 2017/8/3.
 */
public class WeightRandom {

    public static String getServer() {
        Map<String, Integer> serverMap = new HashMap<>();
        serverMap.putAll(IpMap.serverWeightMap);

        Set<String> ipSet = serverMap.keySet();
        Iterator<String> iterator = ipSet.iterator();

        List<String> serverList = new ArrayList<>();
        while (iterator.hasNext()) {
            String server = iterator.next();
            int weight = serverMap.get(server);
            for (int i = 0; i < weight; i++)
                serverList.add(server);
        }

        Random random = new Random();
        int pos = random.nextInt(serverList.size());

        return serverList.get(pos);
    }

}

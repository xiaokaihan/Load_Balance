package com.key.loadbalance.impl;

import com.key.loadbalance.common.IpMap;

import java.util.*;

/**
 * 轮询法（Round Robin）
 * 优点： 试图做到请求转移的绝对均衡;
 * 缺点： 为了做到请求转移的绝对均衡，必须付出相当大的代价，
 *        为了保证pos变量修改的互斥性，需要引入重量级的悲观锁synchronized;
 *        将会导致该段轮询代码的并发吞吐量发生明显下降；
 * Created by Key.Xiao on 2017/8/3.
 */
public class RoundRobin {

    private static Integer pos = 0;

    public static String getServer() {
        // 重建一个map， 避免服务器的上下线导致的并发问题；
        Map<String, Integer> serverMap = new HashMap<>();
        serverMap.putAll(IpMap.serverWeightMap);

        // 取得Ip地址List
        Set<String> ipSet = serverMap.keySet();
        List<String> ipList = new ArrayList<>();
        ipList.addAll(ipSet);

        String server = null;
        synchronized (pos) {
            if (pos >= ipList.size()) {
                pos = 0;
            }
            server = ipList.get(pos);
            pos++;
        }
        return server;
    }
}

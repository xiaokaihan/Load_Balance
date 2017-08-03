package com.key.loadbalance.impl;

import com.key.loadbalance.common.IpMap;

import java.util.*;

/**
 * <p>
 * 原地址哈希法： 获取客户端访问的IP地址值，通过哈希函数计算得到一个数值，
 * 用该数值对服务器列表的大小进行取模运算，得到的结果便是要访问的服务器的序号。
 * 优点： 保证了相同客户端IP地址将会被哈希到同一台后端服务器上，直至后端服务器列表变更
 * 根据此特性可以在服务消费者和服务提供者之间建立有连接的sessionæ会话。
 * <p>
 * 源地址哈希算法的缺点在于：除非集群中服务器的非常稳定，基本不会上下线，
 * 否则一旦有服务器上线、下线，那么通过源地址哈希算法路由到的服务器是服务器上线、下线前路由到的服务器的概率非常低，
 * 如果是session则取不到session，如果是缓存则可能引发”雪崩”。
 * 如果这么解释不适合明白，可以看我之前的一篇文章MemCache超详细解读，一致性Hash算法部分。
 * <p>
 * Created by Key.Xiao on 2017/8/3.
 */
public class HashImpl {

    public static String getServer() {

        Map<String, Integer> serverMap = new HashMap<>();
        serverMap.putAll(IpMap.serverWeightMap);

        Set<String> ipSet = serverMap.keySet();
        List<String> ipList = new ArrayList<>();
        ipList.addAll(ipSet);

        // 在web应用中可通过HttpServlet的getRemoteIp方法获取.
        // 通过客户端的ip也就是remoteIp，取得它的Hash值，对服务器列表的大小取模
        // 结果便是选用的服务器在服务器列表中的索引值。
        String remoteIp = "127.0.0.1";
        int hashCode = remoteIp.hashCode();
        int serverListSize = ipList.size();
        int serverPos = hashCode % serverListSize;

        return ipList.get(serverPos);

    }

}

package com.cheney.study.curator;

import com.cheney.study.ZkConstants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;


public class CuratorCreateSessionDemo {
    public static void main(String[] args) {
        //创建会话的两种方式 normal
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .newClient(ZkConstants.CONNECT_STRING,
                        5000,
                        5000,
                        new ExponentialBackoffRetry(1000, 3)
                );
        curatorFramework.start(); //start方法启动连接

        //fluent风格
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory
                .builder()
                .connectString(ZkConstants.CONNECT_STRING)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("curator")
                .build();

        curatorFramework1.start();
        System.out.println("success");
    }
}

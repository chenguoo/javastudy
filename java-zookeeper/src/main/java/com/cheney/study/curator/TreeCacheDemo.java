package com.cheney.study.curator;

import com.cheney.study.ZkConstants;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.List;


public class TreeCacheDemo {

    private static final String MASTER_PATH="/curator_master_path1";

    private static final int CLIENT_QTY=10; //客户端数量

    public static void main(String[] args) throws Exception {
        System.out.println("创建"+CLIENT_QTY+"个客户端，");
        List<CuratorFramework> clients = Lists.newArrayList();
        List<ExampleClient>     examples = Lists.newArrayList();
        try {
            for (int i = 0; i < CLIENT_QTY; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(ZkConstants.CONNECT_STRING,
                        new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                ExampleClient exampleClient = new ExampleClient(client, MASTER_PATH, "Client:" + i);
                examples.add(exampleClient);
                client.start();
                exampleClient.start();
            }
            System.in.read();
        }finally {
            for ( ExampleClient exampleClient : examples ){
                CloseableUtils.closeQuietly(exampleClient);
            }
            for ( CuratorFramework client : clients ){
                CloseableUtils.closeQuietly(client);
            }
        }
    }
}

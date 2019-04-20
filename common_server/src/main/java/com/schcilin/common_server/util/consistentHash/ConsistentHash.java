package com.schcilin.common_server.util.consistentHash;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.schcilin.common_server.util.consistentHash.util.FNV1_32_HASH;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * @Author: schcilin
 * @Date: 2019/4/14 15:14
 * @Version 1.0
 * @des: 一致性hash算法pojo
 */
public class ConsistentHash {
    /**
     * 物理节点
     */
    private List<String> realNode = Lists.newArrayList();
    /**
     * 物理节点与虚拟节点的对应关系,虚拟节点存储的hash值，所以用integer存储
     */
    private Map<String, List<Integer>> real2VirtualNode = Maps.newHashMap();
    /**
     * 默认虚拟节点的数量
     */
    private int virtualNum = 100;
    /**
     * 排序存储结构，红黑树，key是虚拟节点hash值，value存储的是物理节点,sortedMap为一致性hash环
     */
    private SortedMap<Integer, String> sortedMap = Maps.newTreeMap();

    public ConsistentHash(int virtualNum) {
        this.virtualNum = virtualNum;
    }

    public ConsistentHash() {
    }

    /**
     * 添加服務器
     *
     * @param node 物理节点
     */
    public void addServer(String node) {
        //第一步：添加到物理节点集群上
        this.realNode.add(node);
        //第二步：虚拟节点，虚拟出对应虚拟节点的数量
        String vNode = null;
        int count = 0, i = 0;
        //创建虚拟节点的集合,放进物理与虚拟节点的对应关系中
        List<Integer> virtuaNodes = Lists.newArrayList();
        this.real2VirtualNode.put(node, virtuaNodes);
        //将虚拟节点放进hash环上,
        while (count < this.virtualNum) {
            i++;
            //规避hash碰撞
            vNode = node + "&&" + i;
            //获取虚拟节点hash值
            int hashCode = FNV1_32_HASH.getHash(vNode);
            if (!sortedMap.containsKey(hashCode)) {
                //存放虚拟节点的hashCode值
                virtuaNodes.add(hashCode);
                //放到一致性hash环上面去
                sortedMap.put(hashCode, node);
                count++;
            }

        }

    }

    /**
     * 找到数据的存放节点
     * @param key
     * @return
     */
    public String getServer(String key) {
        //得到hashCode
        int hashCode = FNV1_32_HASH.getHash(key);
        //在hash环上，得到大于该hash值的所有虚拟节点
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hashCode);
        //取得第一个key
        Integer vHash = subMap.firstKey();
        //返回对应的服务器
        return subMap.get(vHash);

    }

    public static void main(String[] args) {
        ConsistentHash consistentHash = new ConsistentHash();
        consistentHash.addServer("19434.13213.32132");
        consistentHash.addServer("19434.13213.32332");
        consistentHash.addServer("19434.13213.32232");
        for (int i = 0; i <10 ; i++) {
            System.out.println("a"+i+"对应的服务器："+consistentHash.getServer("a"+i));
        }
    }
}

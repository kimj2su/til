package com.example.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.clients.producer.internals.StickyPartitionCache;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

// 5개 파티션을 만들고 특정 키값 P001을 고정 파티션에 놔두고 나머지 들을 나머지 파티션으로 보낸다.
public class CustomPartitioner implements Partitioner {

    public static final Logger logger = LoggerFactory.getLogger(CustomPartitioner.class.getName());

    private final StickyPartitionCache stickyPartitionCache = new StickyPartitionCache();

    private String specialKeyName;
    /**
     * 파라미터 Map은 Properties props = new Properties(); 에 정의 된 내용이 넘어온다.
     * @param configs
     */
    @Override
    public void configure(Map<String, ?> configs) {
        specialKeyName = configs.get("custom.specialKey").toString();
    }

    /**
     *
     * @param topic The topic name
     * @param key The key to partition on (or null if no key)
     * @param keyBytes The serialized key to partition on( or null if no key)
     * @param value The value to partition on or null
     * @param valueBytes The serialized value to partition on or null
     * @param cluster The current cluster metadata - 브로커에 있는 토픽의 정보들을 캐싱해서 가지고 있다.
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfoList = cluster.partitionsForTopic(topic);
        //파티션의 사이즈, 개수
        int numPartitions = partitionInfoList.size();
        int numSpecialPartitions = (int)(numPartitions * 0.5);

        int partitionIndex = 0; // 초기화 0으로 안하면 NPE 가능성있음.

        if (keyBytes == null) {
//            return stickyPartitionCache.partition(topic, cluster);
            throw new InvalidRecordException("key should not be null");
        }

        //numSpecialPartitions 가 5개면 2개가 나온다.
        //Utils.toPositive(Utils.murmur2(keyBytes)) % numSpecialPartitions; 기존 로직은 keyBytes가 P001로 고정된 값이라 0,1번 파티션만 나온다.
        if (((String) key).equals(specialKeyName)) {
            partitionIndex = Utils.toPositive(Utils.murmur2(valueBytes)) % numSpecialPartitions;
        } else {
            //연산 로직은 if문은 0,1 만 나오고 else는 다 나오므로 (5-2) == 0,1,2 에서 2씩 더해준 파티션이 나오게 해준다.
            partitionIndex = Utils.toPositive(Utils.murmur2(keyBytes)) % (numPartitions - numSpecialPartitions) + numSpecialPartitions;
        }

        logger.info("key : {} is sent to partition: {}", key.toString(), partitionIndex);

        return partitionIndex;
    }

    @Override
    public void close() {

    }


}

package leite.hubei.bigdata.ETL.demo;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MessageSendDemo {
    private KafkaProducer<String, String> producer;

    public MessageSendDemo(){
        producer = new KafkaProducer<String, String>(createConsumerConfig());
    }

    public void send(String topic, Object obj){
        ProducerRecord<String, String> pdMessage = new ProducerRecord<String, String>(topic, JSON.toJSONString(obj));
        producer.send(pdMessage);
    }

    private static Properties createConsumerConfig()
    {
        //视情况修改zookeeper集群地址和groupid
        Properties props = new Properties();

        props.put("zookeeper.connect", "cm01:218,cm02:218,cdh12:218,cdh13:218,cdh14:2181"); // cdh01:2181,cdh02:2181
        props.put("zookeeper.session.timeout.ms", "40000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("group.id", "grouptest01");

        props.put("producer.type", "async");  // 设置为异步模式

        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        props.put("bootstrap.servers", "cdh05:9092,cdh06:9092,cdh07:9092,cdh08:9092,cdh09:9092,cdh10:9092,cdh11:9092,cdh12:9092");  // broker集群
        //性能优化,需要注意
        props.put("acks", "1");  // 折中的安全策略
        props.put("retries", "1");
        props.put("batch.size", "524288");  // 512kb
                
        props.put("linger.ms", "0");  // 在满足batch.zsize前的等待时间
        props.put("buffer.memory", "536870912");  // 512 MB
        props.put("compression.type", "snappy");  // 压缩类型
        props.put("num.replica.fetchers", "3");
        return props;
    }
}

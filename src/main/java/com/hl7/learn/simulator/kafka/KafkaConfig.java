package com.hl7.learn.simulator.kafka;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

	/**
	 * 
	 */
	@Value("${kafka.bootstrapserver}")
	private String kafkaBootstrapServer;
	/**
	 * 
	 */
	@Value("${kafka.clientidconfig}")
	private String kafkaclientidconfig;
	/**
	 * 
	 */
	@Value("${kafka.topic.keys}")
	private String kafkaTopicKeylist;
	/**
	 * 
	 */
	@Value("${kafka.topics.separator}")
	private String kafkaTopicListSeparator;
	/**
	 * 
	 */
	private Map<String, Object> kafkaProperties = new HashMap<>();
	/**
	 * 
	 */
	private AdminClient client;
	/**
	 * 
	 */
	@Autowired
	private Environment env;

	/**
	 * 
	 */
	@PostConstruct
	public void initialize() {
		kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
		kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		kafkaProperties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaclientidconfig);
		kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		client = AdminClient.create(kafkaProperties);
		List<String> topicKeyList = Stream.of(kafkaTopicKeylist.split(kafkaTopicListSeparator))
				.collect(Collectors.toList());
		topicKeyList.stream().map(k -> env.getProperty(k)).forEach(this::createKafkaTopic);
	}

	/**
	 * @param topicName
	 */
	public void createKafkaTopic(String topicName) {
		try {
			String partition = env.getProperty("kafka.topic." + topicName + ".partition");
			String replication = env.getProperty("kafka.topic." + topicName + ".replication");
			NewTopic topic = new NewTopic(topicName, Integer.parseInt(partition == null ? "1" : partition),
					Short.parseShort(replication == null ? "1" : replication));
			CreateTopicsResult createTopicsResult = client.createTopics(Collections.singletonList(topic));
			createTopicsResult.values().get(topicName).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public Map<String, Object> getKafkaProperties() {
		return kafkaProperties;
	}

}

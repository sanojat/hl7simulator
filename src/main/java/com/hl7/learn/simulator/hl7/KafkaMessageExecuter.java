package com.hl7.learn.simulator.hl7;

import java.io.IOException;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.hl7.fhir.exceptions.FHIRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.hl7.learn.simulator.kafka.KafkaMessageProducer;
import com.hl7.learn.simulator.model.HL7MessageConfigModel;
import com.hl7.learn.simulator.model.Hl7MessageConfig;
import com.hl7.learn.simulator.type.DataType;

/**
 * @author 1013744
 *
 */
@Component
public class KafkaMessageExecuter {

	/**
	 * 
	 */
	@Autowired
	MessageExecutionContext context;
	/**
	 * 
	 */
	@Autowired
	HL7DataBuilder dataBuilder;
	/**
	 * 
	 */
	@Autowired
	KafkaMessageProducer kafkaMessageProducer;
	/**
	 * 
	 */
	@Autowired
	Environment env;

	/**
	 * 
	 */
	@PostConstruct
	public void execute() {
		HL7MessageConfigModel configModel = context.getExecutioncontext();
		configModel.getMessageConfigs().stream().forEach(t -> {
			t.getParamConfigs().parallelStream().forEach(c -> {
				SchedulerTask taskExecuter = context.getSchedulingMap(t);
				TimerTask task = new TimerTask() {
					String topicName = "";
					{
						topicName = getTopicName(t);
					}

					@Override
					public void run() {
						try {
							System.out.println("Task for >>" + t.getPid() + ": kafka topic >>" + topicName);
							kafkaMessageProducer.publish(topicName, t.getPid(), dataBuilder.getMessage(t));
						} catch (FHIRException | IOException e) {
							e.printStackTrace();
						}
					}
				};
				taskExecuter.schedule(task);
			});

		});
	}

	/**
	 * @param config
	 * @return
	 */
	public String getTopicName(Hl7MessageConfig cf) {
		String topicName = "";
		if (DataType.NUMERIC.equals(cf.getDataType())) {
			topicName = env.getProperty("kafka.topic.numeric");
		} else if (DataType.WAVEFORM.equals(cf.getDataType())) {
			topicName = env.getProperty("kafka.topic.waveform");
		}
		return topicName;
	}

}

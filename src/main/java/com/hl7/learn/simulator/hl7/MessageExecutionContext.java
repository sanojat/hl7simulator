package com.hl7.learn.simulator.hl7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hl7.learn.simulator.model.HL7MessageConfigModel;
import com.hl7.learn.simulator.model.Hl7MessageConfig;
import com.hl7.learn.simulator.model.ParameterConfig;
import com.hl7.learn.simulator.type.DataType;
import com.hl7.learn.simulator.util.JsonUtil;

/**
 * @author 1013744
 *
 */
@Component
@PropertySource("classpath:dataconfig.properties")
public class MessageExecutionContext {

	/**
	 * 
	 */
	@Value("classpath:hl7messagconfig.json")
	private Resource hl7MessageConfigFile;

	/**
	 * 
	 */
	@Autowired
	private Environment env;
	/**
	 * 
	 */
	private HL7MessageConfigModel hl7MessageConfig;

	/**
	 * 
	 */
	private Map<String, SchedulerTask> schedulingMap = new ConcurrentHashMap<>();

	/**
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * 
	 */
	@PostConstruct
	public void intialize() throws JsonParseException, JsonMappingException, IOException {
		hl7MessageConfig = JsonUtil.getObject(hl7MessageConfigFile.getFile(), HL7MessageConfigModel.class);
		hl7MessageConfig.getMessageConfigs().parallelStream().filter(h -> DataType.NUMERIC.equals(h.getDataType()))
				.forEach(c -> c.getParamConfigs().forEach(this::initializeNumericValues));
		hl7MessageConfig.getMessageConfigs().parallelStream().filter(h -> DataType.WAVEFORM.equals(h.getDataType()))
				.forEach(c -> c.getParamConfigs().forEach(this::initializeWaveformValues));
		hl7MessageConfig.getMessageConfigs().stream().forEach(c -> {
			SchedulerTask task = new SchedulerTask(c.getEntityId() + "-" + c.getDataType());
			task.setPeriod(c.getTimeInterval());
			schedulingMap.putIfAbsent(c.getEntityId() + "-" + c.getDataType(), task);
		});

	}

	/**
	 * @param config
	 */
	private void initializeNumericValues(ParameterConfig config) {
		config.setMinValue(Double.parseDouble(env.getProperty(config.getParamName() + ".minvalue")));
		config.setMaxValue(Double.parseDouble(env.getProperty(config.getParamName() + ".maxvalue")));
		List<Double> values = Stream.of(env
				.getProperty(
						config.getKeyToValues() == null ? config.getParamName() + ".values" : config.getKeyToValues())
				.split(env.getProperty(config.getValueSeparator()) == null ? env.getProperty("default.separator")
						: env.getProperty(config.getValueSeparator())))
				.map(Double::parseDouble).collect(Collectors.toList());
		config.setNumericValueList((ArrayList<Double>) values);
	}

	/**
	 * @param config
	 */
	private void initializeWaveformValues(ParameterConfig config) {
		config.setMinValue(Double.parseDouble(env.getProperty(config.getParamName() + ".minvalue")));
		config.setMaxValue(Double.parseDouble(env.getProperty(config.getParamName() + ".maxvalue")));
		List<String> values = Stream.of(env
				.getProperty(
						config.getKeyToValues() == null ? config.getParamName() + ".values" : config.getKeyToValues())
				.split(env.getProperty(config.getValueSeparator()) == null ? env.getProperty("default.separator")
						: env.getProperty(config.getValueSeparator())))
				.collect(Collectors.toList());
		config.setStringValueList((ArrayList<String>) values);
		config.setSequentialCounter(0);
		config.setSequentialCounterMax(config.getStringValueList().size());
		config.setSequentialDataBlockLength(config.getSequentialDataBlockLength() == 0 ? 1
				: config.getSequentialDataBlockLength() > config.getStringValueList().size()
						? config.getStringValueList().size()
						: config.getSequentialDataBlockLength());

	}

	/**
	 * @return
	 */
	public HL7MessageConfigModel getExecutioncontext() {
		return hl7MessageConfig;
	}

	/**
	 * @param key
	 * @return
	 */
	public SchedulerTask getSchedulingMap(String key) {
		return schedulingMap.get(key);
	}

	/**
	 * @param config
	 * @return
	 */
	public SchedulerTask getSchedulingMap(Hl7MessageConfig config) {
		return schedulingMap.get(config.getEntityId() + "-" + config.getDataType().name());
	}

}

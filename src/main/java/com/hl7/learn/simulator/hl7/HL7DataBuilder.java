package com.hl7.learn.simulator.hl7;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hl7.learn.simulator.model.Hl7MessageConfig;
import com.hl7.learn.simulator.model.ParameterConfig;
import com.hl7.learn.simulator.type.DataType;
import com.hl7.learn.simulator.type.NumericParameter;
import com.hl7.learn.simulator.type.ParamValueSelector;
import com.hl7.learn.simulator.type.WaveformParameter;
import com.hl7.learn.simulator.util.SimulatorHelper;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

/**
 * @author 1013744
 *
 */
@Component
public class HL7DataBuilder {

	/**
	 * 
	 */
	@Autowired
	private HL7DataTemplateHandler templateHandler;
	/**
	 * 
	 */
	private static IParser parser = FhirContext.forR4().newJsonParser();

	/**
	 * 
	 */
	private static final String WAVEFORM_VALUE_CONNECTOR = " ";

	/**
	 * @param type
	 * @param patientId
	 * @param config
	 * @return
	 * @throws FHIRException
	 * @throws IOException
	 */
	public Bundle getData(DataType type, String patientId, ParameterConfig config) throws FHIRException, IOException {
		switch (type) {
		case NUMERIC:
			return getNumericBundle(patientId, config);
		case WAVEFORM:
			return getWaveformBundle(patientId, config);
		default:
			break;
		}
		return null;
	}

	/**
	 * @param messageConfig
	 * @return
	 * @throws FHIRException
	 * @throws IOException
	 */
	public String getMessage(Hl7MessageConfig messageConfig) throws FHIRException, IOException {
		Bundle bundle = getMessageBundle(messageConfig);
		return parser.encodeResourceToString(bundle);
	}

	/**
	 * @param messageConfig
	 * @return
	 * @throws FHIRException
	 * @throws IOException
	 */
	public Bundle getMessageBundle(Hl7MessageConfig messageConfig) throws FHIRException, IOException {
		Bundle bundle = getData(messageConfig.getDataType(), messageConfig.getEntityId(),
				messageConfig.getParamConfigs().get(0));
		messageConfig.getParamConfigs().parallelStream().skip(1).forEach(m -> {
			try {
				Bundle b = getData(messageConfig.getDataType(), messageConfig.getEntityId(), m);
				bundle.getEntry().addAll(b.getEntry());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return bundle;
	}

	/**
	 * @param patientId
	 * @param config
	 * @return
	 * @throws IOException
	 * @throws FHIRException
	 */
	public Bundle getNumericBundle(String patientId, ParameterConfig config) throws IOException, FHIRException {
		Bundle bundle = templateHandler.getNumericBundle(NumericParameter.valueOf(config.getParamName()));
		List<BundleEntryComponent> entryList = bundle.getEntry();
		BundleEntryComponent component = entryList.get(0);
		Resource resource = component.getResource();
		if (resource.getClass().equals(Observation.class)) {
			Reference reference = new Reference();
			reference.setReference("Patient/" + patientId);
			Observation observation = (Observation) resource;
			observation.setSubject(reference);
			observation.setEffective(SimulatorHelper.getEffectiveDateTime());
			if (ParamValueSelector.RANDOM.equals(config.getValueSelector())) {
				Double value = SimulatorHelper.getRandomValue(config.getNumericValueList());
				if (value % 10 == 0) {
					observation.getValueQuantity().setValue(new BigDecimal(value));
				} else {
					observation.getValueQuantity().setValue(value);
				}
			}
		}
		return bundle;
	}

	/**
	 * @param patientId
	 * @param config
	 * @return
	 * @throws IOException
	 * @throws FHIRException
	 */
	public Bundle getWaveformBundle(String patientId, ParameterConfig config) throws IOException, FHIRException {
		Bundle bundle = templateHandler.getWaveformBundle(WaveformParameter.valueOf(config.getParamName()));
		List<BundleEntryComponent> entryList = bundle.getEntry();
		BundleEntryComponent component = entryList.get(0);
		Resource resource = component.getResource();
		if (resource.getClass().equals(Observation.class)) {
			Reference reference = new Reference();
			reference.setReference("Patient/" + patientId);
			Observation observation = (Observation) resource;
			observation.setSubject(reference);
			observation.setEffective(SimulatorHelper.getEffectiveDateTime());
			if (ParamValueSelector.SEQUENTIAL.equals(config.getValueSelector())) {
				if (config.getSequentialCounter() >= config.getSequentialCounterMax()) {
					config.setSequentialCounter(0);
				}
				if (config.getSequentialDataBlockLength() > 1) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < config.getSequentialDataBlockLength(); i++) {
						if (config.getSequentialCounter() >= config.getSequentialCounterMax()) {
							config.setSequentialCounter(0);
						}
						sb.append(config.getStringValueList().get(config.getSequentialCounter()))
								.append(WAVEFORM_VALUE_CONNECTOR);
						config.setSequentialCounter(config.getSequentialCounter() + 1);
					}
					// sb.deleteCharAt(sb.lastIndexOf(""));
					observation.getValueSampledData().setData(sb.toString().trim());
				} else {
					observation.getValueSampledData()
							.setData(config.getStringValueList().get(config.getSequentialCounter()));
					config.setSequentialCounter(config.getSequentialCounter() + 1);
				}
			}
		}
		return bundle;
	}

}

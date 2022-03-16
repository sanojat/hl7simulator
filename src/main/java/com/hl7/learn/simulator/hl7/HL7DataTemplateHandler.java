package com.hl7.learn.simulator.hl7;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.hl7.fhir.r4.model.Bundle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.hl7.learn.simulator.type.NumericParameter;
import com.hl7.learn.simulator.type.WaveformParameter;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

/**
 * @author 1013744
 *
 */
@Component
public class HL7DataTemplateHandler {

	/**
	 * 
	 */
	@Value("classpath:MDC_ECG_HEART_RATE_template.json")
	private Resource heartRateNumericTemplate;

	/**
	 * 
	 */
	@Value("classpath:MDC_ECG_AMPL_ST_I_template.json")
	private Resource ecgAmplSt1NumericTemplate;

	/**
	 * 
	 */
	@Value("classpath:MDC_ECG_ELEC_POTL_I_template.json")
	private Resource ecgWaveformst1Template;

	/**
	 * 
	 */
	private Map<NumericParameter, String> numericTemplateCache = new ConcurrentHashMap<>();
	/**
	 * 
	 */
	private Map<WaveformParameter, String> waveformTemplateCache = new ConcurrentHashMap<>();

	/**
	 * 
	 */
	private static IParser parser = FhirContext.forR4().newJsonParser();

	/**
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public Bundle getNumericBundle(NumericParameter type) throws IOException {
		Bundle bundle = null;
		switch (type) {
		case MDC_ECG_HEART_RATE:
			bundle = getNumericTemplate(type, heartRateNumericTemplate);
			break;
		case MDC_ECG_AMPL_ST_I:
			bundle = getNumericTemplate(type, ecgAmplSt1NumericTemplate);
			break;
		default:
			break;
		}
		return bundle;

	}

	/**
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public Bundle getWaveformBundle(WaveformParameter type) throws IOException {
		Bundle bundle = null;
		switch (type) {
		case MDC_ECG_ELEC_POTL_I:
			bundle = getWaveformTemplate(type, ecgWaveformst1Template);
			break;

		default:
			break;
		}
		return bundle;
	}

	/**
	 * @param templateString
	 * @return
	 * @throws IOException
	 */
	public Bundle prepareBundle(String templateString) throws IOException {
		Bundle bundle = parser.parseResource(Bundle.class, templateString);
		return bundle;
	}

	/**
	 * @param type
	 * @param template
	 * @return
	 * @throws IOException
	 */
	private Bundle getNumericTemplate(NumericParameter type, Resource template) throws IOException {
		return getTemplate(type, template, numericTemplateCache);
	}

	/**
	 * @param type
	 * @param template
	 * @return
	 * @throws IOException
	 */
	private Bundle getWaveformTemplate(WaveformParameter type, Resource template) throws IOException {
		return getTemplate(type, template, waveformTemplateCache);
	}

	/**
	 * @param <T>
	 * @param type
	 * @param template
	 * @param templateCache
	 * @return
	 * @throws IOException
	 */
	private <T> Bundle getTemplate(T t, Resource template, Map<T, String> cache) throws IOException {
		cache.computeIfAbsent(t, k -> {
			String templateStr = null;
			try {
				templateStr = new String(Files.readAllBytes(template.getFile().toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return templateStr;
		});
		return this.prepareBundle(cache.get(t));
	}
}

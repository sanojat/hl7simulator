package com.hl7.learn.simulator.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

import org.hl7.fhir.r4.model.DateTimeType;

/**
 * @author 1013744
 *
 */
public class SimulatorHelper {

	/**
	 * 
	 */
	private static Random random = new Random();

	/**
	 * @return
	 */
	public static DateTimeType getEffectiveDateTime() {
		LocalDateTime start = LocalDateTime.now();
		ZonedDateTime startZone = start.atZone(ZoneOffset.of("+05:30"));
		DateTimeType effectiveDateTime = new DateTimeType(startZone.toString());
		return effectiveDateTime;
	}

	/**
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> T getRandomValue(List<T> list) {
		int size = list.size();
		int randomIndex = random.nextInt(size);
		return list.get(randomIndex);
	}

}

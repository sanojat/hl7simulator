package com.hl7.learn.simulator.model;

import java.util.ArrayList;

import com.hl7.learn.simulator.type.ParamValueSelector;

/**
 * @author 1013744
 *
 */
public class ParameterConfig {

	/**
	 * 
	 */
	private String paramName;
	/**
	 * 
	 */
	private ParamValueSelector valueSelector;
	/**
	 * 
	 */
	private double minValue;
	/**
	 * 
	 */
	private double maxValue;
	/**
	 * 
	 */
	private String keyToValues;
	/**
	 * 
	 */
	private String valueSeparator;
	/**
	 * 
	 */
	private ArrayList<Double> numericValueList;

	/**
	 * 
	 */
	private ArrayList<String> stringValueList;

	/**
	 * 
	 */
	private int sequentialCounter;
	
	/**
	 * 
	 */
	private int sequentialDataBlockLength;

	/**
	 * 
	 */
	private int sequentialCounterMax;

	/**
	 * @return
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return
	 */
	public ParamValueSelector getValueSelector() {
		return valueSelector;
	}

	/**
	 * @param valueSelector
	 */
	public void setValueSelector(ParamValueSelector valueSelector) {
		this.valueSelector = valueSelector;
	}

	/**
	 * @return
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * @param minValue
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	/**
	 * @return
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * @return
	 */
	public ArrayList<Double> getNumericValueList() {
		return numericValueList;
	}

	/**
	 * @param valueList
	 */
	public void setNumericValueList(ArrayList<Double> numericValueList) {
		this.numericValueList = numericValueList;
	}

	/**
	 * @return
	 */
	public String getKeyToValues() {
		return keyToValues;
	}

	/**
	 * @param keyToValues
	 */
	public void setKeyToValues(String keyToValues) {
		this.keyToValues = keyToValues;
	}

	/**
	 * @return
	 */
	public String getValueSeparator() {
		return valueSeparator;
	}

	/**
	 * @param valueSeparator
	 */
	public void setValueSeparator(String valueSeparator) {
		this.valueSeparator = valueSeparator;
	}

	/**
	 * @return
	 */
	public ArrayList<String> getStringValueList() {
		return stringValueList;
	}

	/**
	 * @param stringValueList
	 */
	public void setStringValueList(ArrayList<String> stringValueList) {
		this.stringValueList = stringValueList;
	}

	/**
	 * @return
	 */
	public int getSequentialCounter() {
		return sequentialCounter;
	}

	/**
	 * @param sequentialCounter
	 */
	public void setSequentialCounter(int sequentialCounter) {
		this.sequentialCounter = sequentialCounter;
	}

	/**
	 * @return
	 */
	public int getSequentialCounterMax() {
		return sequentialCounterMax;
	}

	/**
	 * @param sequentialCounterMax
	 */
	public void setSequentialCounterMax(int sequentialCounterMax) {
		this.sequentialCounterMax = sequentialCounterMax;
	}

	/**
	 * @return
	 */
	public int getSequentialDataBlockLength() {
		return sequentialDataBlockLength;
	}

	/**
	 * @param sequentialDataBlockLength
	 */
	public void setSequentialDataBlockLength(int sequentialDataBlockLength) {
		this.sequentialDataBlockLength = sequentialDataBlockLength;
	}
}

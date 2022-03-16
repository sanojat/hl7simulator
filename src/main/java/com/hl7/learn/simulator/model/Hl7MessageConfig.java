package com.hl7.learn.simulator.model;

import java.util.ArrayList;

import com.hl7.learn.simulator.type.DataType;

/**
 * @author 1013744
 *
 */
public class Hl7MessageConfig{
	/**
	 * 
	 */
	private String entityId;
	/**
	 * 
	 */
	private String pid;
	/**
	 * 
	 */
	private DataType dataType;
	/**
	 * 
	 */
	private ArrayList<ParameterConfig> paramConfigs;
	/**
	 * 
	 */
	private long timeInterval;

	/**
	 * @return
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return
	 */
	public long getTimeInterval() {
		return timeInterval;
	}

	/**
	 * @param timeInterval
	 */
	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * @return
	 */
	public ArrayList<ParameterConfig> getParamConfigs() {
		return paramConfigs;
	}

	/**
	 * @param paramConfigs
	 */
	public void setParamConfigs(ArrayList<ParameterConfig> paramConfigs) {
		this.paramConfigs = paramConfigs;
	}

	/**
	 * @return
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

}

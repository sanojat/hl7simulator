package com.hl7.learn.simulator.model;

import java.util.ArrayList;

/**
 * @author 1013744
 *
 */
public class HL7MessageConfigModel {

	/**
	 * 
	 */
	private ArrayList<Hl7MessageConfig> messageConfigs;

	/**
	 * @return
	 */
	public ArrayList<Hl7MessageConfig> getMessageConfigs() {
		return messageConfigs;
	}

	/**
	 * @param messageConfigs
	 */
	public void setMessageConfigs(ArrayList<Hl7MessageConfig> messageConfigs) {
		this.messageConfigs = messageConfigs;
	}

}

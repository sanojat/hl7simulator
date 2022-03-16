package com.hl7.learn.simulator.vo;

/**
 * @author 1013744
 *
 */
public class ThreadStatus {

	/**
	 * 
	 */
	private String threadName;
	/**
	 * 
	 */
	private RunningStatus status;
	/**
	 * 
	 */
	private String exceptionMsg;

	/**
	 * @return
	 */
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	/**
	 * @param exceptionMsg
	 */
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	/**
	 * @return
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @param threadName
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	/**
	 * @return
	 */
	public RunningStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(RunningStatus status) {
		this.status = status;
	}

}

package com.hl7.learn.simulator.hl7;

import java.util.Timer;
import java.util.TimerTask;

import com.hl7.learn.simulator.vo.RunningStatus;
import com.hl7.learn.simulator.vo.ThreadStatus;

/**
 * @author 1013744
 *
 */
class SchedulerTask {
	/**
	 * 
	 */
	private long period;
	/**
	 * 
	 */
	private Timer timer;
	/**
	 * 
	 */
	private ThreadStatus status;
	/**
	 * 
	 */
	private String testThreadName;

	/**
	 * @return
	 */
	public ThreadStatus getThreadStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setThreadStatus(ThreadStatus status) {
		this.status = status;
	}

	/**
	 * @param threadName
	 */
	SchedulerTask(String threadName) {
		timer = new Timer(threadName);
		status = new ThreadStatus();
		status.setExceptionMsg("NO EXCEPTION FOUND");
		status.setStatus(RunningStatus.STOPED);
		status.setThreadName(threadName);
		testThreadName = threadName;
	}

	/**
	 * @param threadName
	 * @param exceptionString
	 */
	SchedulerTask(String threadName, String exceptionString) {
		status = new ThreadStatus();
		status.setExceptionMsg(exceptionString);
		status.setStatus(RunningStatus.STOPED);
		status.setThreadName(threadName);
		testThreadName = threadName;
	}

	/**
	 * @param task
	 */
	public void schedule(TimerTask task) {
		if (timer != null && RunningStatus.STOPED.equals(status.getStatus())) {
			timer.scheduleAtFixedRate(task, 0, period);
			status.setStatus(RunningStatus.RUNNING);
		}
	}

	/**
	 * 
	 */
	public void cancel() {
		if (timer != null && RunningStatus.RUNNING.equals(status.getStatus())) {
			System.out.println(" cancelling invoked :" + testThreadName);
			timer.cancel();
			status.setStatus(RunningStatus.STOPED);
		}
	}

	/**
	 * @return
	 */
	public long getPeriod() {
		return period;
	}

	/**
	 * @param period
	 */
	public void setPeriod(long period) {
		this.period = period;
	}
}
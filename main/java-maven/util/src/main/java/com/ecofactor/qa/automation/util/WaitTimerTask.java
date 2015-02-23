package com.ecofactor.qa.automation.util;

import java.util.Date;
import java.util.TimerTask;

public class WaitTimerTask extends TimerTask {

	public void run() {
		//dummy task to wait
		System.out.println(new Date());
	}
}

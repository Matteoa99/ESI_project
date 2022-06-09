package com.unicam.newemployee;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ExternalCheck implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String empId = (String) execution.getVariable("empID");
		if(empId == "1") // Already exist
			execution.setVariable("extNew", false);
		else 
			execution.setVariable("extNew", true);
	}

}

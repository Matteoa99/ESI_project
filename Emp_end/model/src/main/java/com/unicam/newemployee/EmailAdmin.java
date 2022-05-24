package com.unicam.newemployee;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class EmailAdmin implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String, Object> variables = execution.getVariables();
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("end_emp_msg_admin")
				.setVariables(variables).correlate();
	}

}

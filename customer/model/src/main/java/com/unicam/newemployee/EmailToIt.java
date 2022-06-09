package com.unicam.newemployee;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class EmailToIt implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String, Object> variables = execution.getVariables();
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("mgr_to_it")
				.setVariables(variables).correlate();

	}

}

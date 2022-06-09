package com.unicam.employeetransfert;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class EmailToIT implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Map<String, Object> variables = execution.getVariables();
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("hr_to_it")
				.setVariables(variables).correlate();
	}

}

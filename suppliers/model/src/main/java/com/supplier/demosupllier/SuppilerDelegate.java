package com.supplier.demosupllier;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SuppilerDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
				Map<String, Object> variables = execution.getVariables();
				execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("it_dept_message")
						.setVariables(variables).correlate();

	}

}

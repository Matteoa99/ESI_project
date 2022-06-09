import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class EndEmployment {
	private final static Logger LOGGER = Logger.getLogger(EndEmployment.class.getName());

	public static void main(String[] args) {

		recordHR();

		deactivateUser();

		sentCredentialToEmployee();

	}

	static void recordHR() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("record_hr_system").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			// Get a process variable
			String empId = externalTask.getVariable("empId");

			LOGGER.info("Record in HR system matricola: " + empId+"\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void deactivateUser() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("deactivate_user").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			String empId = externalTask.getVariable("empId");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void sentCredentialToEmployee() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("end_emp").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("End employment relationship");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

}

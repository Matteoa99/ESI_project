import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class EmpTransferWorker {
	private final static Logger LOGGER = Logger.getLogger(EmpTransferWorker.class.getName());

	public static void main(String[] args) {

		recordIntoDb();

		createProfile();

		sentCredentialToEmployee();
	}

	static void recordIntoDb() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("add_db").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			String employee_id = externalTask.getVariable("employee_id");
			String new_department = externalTask.getVariable("new_department");

			LOGGER.info("New employee ticket created name: " + employee_id + " new department: " + new_department + "\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void createProfile() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("create_profile").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("New profile create\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void sentCredentialToEmployee() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("sent_employee").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("New information sent to employee\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}
	
	static void closeTicket() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("close_ticket").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Ticket close\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

}

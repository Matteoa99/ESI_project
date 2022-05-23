import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class NewEmployeeWorker {
	private final static Logger LOGGER = Logger.getLogger(NewEmployeeWorker.class.getName());

	public static void main(String[] args) {

		createEntryHR();

		newUser();

		createProfile();

		sentCredentialToEmployee();

	}

	static void createEntryHR() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("new_emp").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			// Put your business logic here
			// Get a process variable
			String firstname = externalTask.getVariable("name");
			String surname = externalTask.getVariable("surname");
			Date dateofbirth = externalTask.getVariable("dateofbirth");
			String empId = externalTask.getVariable("empId");

			LOGGER.info("New employee HR system\nName: " + firstname + " surname: " + surname + " date of birth: "
					+ dateofbirth + " matricola: " + empId);

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void newUser() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("add_on_db").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			LOGGER.info("User added in User Managment System\n");

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

			LOGGER.info("New profile create");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void sentCredentialToEmployee() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("sent_to_employee").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Credenttial sent to the new employee");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

}

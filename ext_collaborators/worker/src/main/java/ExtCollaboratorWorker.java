import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class ExtCollaboratorWorker {
	private final static Logger LOGGER = Logger.getLogger(ExtCollaboratorWorker.class.getName());

	public static void main(String[] args) {

		createTicket();

		newUser();

		createProfile();

		sentCredentialToEmployee();
		
		closeTicket();
		
		updateEntry();

	}

	static void createTicket() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("create_ticket").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			// Put your business logic here
			// Get a process variable
			String name = externalTask.getVariable("name");
			String surname = externalTask.getVariable("surname");
			Date startcollab = externalTask.getVariable("startcollab");
			Date endcollab = externalTask.getVariable("endcollab");
			String empId = externalTask.getVariable("empID");

			LOGGER.info("New employee ticket created\nName: " + name + " surname: " + surname + " start collaboration: "
					+ startcollab + " end collaboration: "+ endcollab + " matricola: " + empId+ "\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void newUser() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("create_entry").lockDuration(1000).handler((externalTask, externalTaskService) -> {
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
		client.subscribe("notify_emp").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Credenttial sent to the new employee\n");

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

			LOGGER.info("Ticket close");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}
	
	static void updateEntry() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("update_entry").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Entry updated\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

}

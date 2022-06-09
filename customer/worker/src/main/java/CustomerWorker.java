import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class CustomerWorker {
	private final static Logger LOGGER = Logger.getLogger(CustomerWorker.class.getName());

	public static void main(String[] args) {

		createEntry();
		
		closeTicketMgr();
		
		closeTicketIt();
		
		emailCustomer();

	}

	static void createEntry() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("create_entry").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			String company_name = externalTask.getVariable("company_name");
			Date entry_date = externalTask.getVariable("entry_date");

			LOGGER.info("New entry created created Company Name: " + company_name + " entry date: " + entry_date + "\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	static void emailCustomer() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("email_customer").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Sent email to customer\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}
	
	static void closeTicketMgr() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("close_ticket_mgr").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Ticket close into MGR");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}
	
	static void closeTicketIt() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("close_ticket_it").lockDuration(1000).handler((externalTask, externalTaskService) -> {

			LOGGER.info("Ticket close into IT dept");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

}

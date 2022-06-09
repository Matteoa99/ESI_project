import java.util.Date;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;
public class SupplierWorker {
	private final static Logger LOGGER = Logger.getLogger(SupplierWorker.class.getName());

	public static void main(String[] args) {

		createEntryERP();

		creEntryOnApp();

		closeTicekt();

	}

	static void createEntryERP() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("add_sup_erpDB").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			// Put your business logic here
			// Get a process variable
			String companyName = externalTask.getVariable("companyname");
			Date entrydate = externalTask.getVariable("entrydate");

			LOGGER.info("New Supliers create in ERP system Company Name: "
					+ companyName + " Entry date: "+ entrydate+"\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}	

	static void creEntryOnApp() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("create_entry_app").lockDuration(1000).handler((externalTask, externalTaskService) -> {
			String companyName = externalTask.getVariable("companyname");
			LOGGER.info("Create entry on Intranet application for company: "+companyName+"\n");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

	
	static void closeTicekt() {
		ExternalTaskClient client = ExternalTaskClient.create().baseUrl("http://localhost:8080/engine-rest")
				.asyncResponseTimeout(10000) // long polling timeout
				.build();

		// subscribe to an external task topic as specified in the process
		client.subscribe("close_ticekt").lockDuration(1000).handler((externalTask, externalTaskService) -> {
            
			String role = externalTask.getVariable("role");
			LOGGER.info("Supplier Role is: "+role+"\n");
			LOGGER.info("Close ticekt");

			// Complete the task
			externalTaskService.complete(externalTask);
		}).open();
	}

}

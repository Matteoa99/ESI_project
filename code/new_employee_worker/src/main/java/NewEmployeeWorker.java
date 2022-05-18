import java.util.Date;
import java.util.logging. Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class NewEmployeeWorker {
private final static Logger LOGGER = Logger .getLogger (NewEmployeeWorker.class.getName());

public static void main (String[] args) {

ExternalTaskClient client = ExternalTaskClient.create()
.baseUrl("http://localhost:8080/engine-rest")
.asyncResponseTimeout (10000) // long polling timeout
.build();

// subscribe to an external task topic as specified in the process
client.subscribe ("new_emp")
.lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
.handler((externalTask,externalTaskService) -> {
// Put your business logic here
// Get a process variable
String firstname = externalTask.getVariable ("firstname");
String surname = externalTask.getVariable ("surname");
Date dateofbirth = externalTask.getVariable ("dateofbirth");
String empId = externalTask.getVariable ("empId");

LOGGER.info ("New employee added\nName: " + firstname + " surname: " + surname + " date of birth: " + dateofbirth + " matricola: " + empId);

    // Complete the task
externalTaskService.complete(externalTask);
})
  .open();
 }
}

package stepdefinationfile;

import Automation.DemoQa;
import io.cucumber.java.en.Given;

public class Stepdef {
	
	@Given("Need to launch DemoQA URL")
	public void need_to_launch_demo_qa_url() {
	    // Write code here that turns the phrase above into concrete actions
		

	    DemoQa login=new DemoQa();
	    login.test();
	    System.out.println("step defination file executed");
	    throw new io.cucumber.java.PendingException();
	    
	    
	    
	}


}

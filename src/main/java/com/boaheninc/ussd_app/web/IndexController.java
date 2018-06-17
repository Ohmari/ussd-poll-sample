package com.boaheninc.ussd_app.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boaheninc.ussd_app.model.UssdParameters;
@RestController
@RequestMapping("/api")
public class IndexController {
    
	@PostMapping(value="/ussd/random-poll", consumes = {"application/x-www-form-urlencoded"})
    public String index(UssdParameters params) {
    	String response = "", text, phoneNumber;
    	String[] textArray;
    	String[] data;
    	
    	text = params.getText();
    	phoneNumber = params.getPhoneNumber();
    	
    	/*
    	 * extract individual strings from text that are concatenated by *
    	 * Assumptions are neither of these parameters will be null during the 
    	 * initiation of the USSD call hence no checks for null in the code
    	 */
    	
    	textArray = text.split("\\*");
        if(text == "") {
    		response = "CON Welcome to the DISSOLVE GFA Polls.\n"
    				+ "1. Press 1 to Continue to Polls\n"
    				+ "2. Press 0 to exit Polls";
        }else if(text.contentEquals("1")) {
      		  response = "CON Please provide your name\n"; 
        }else if(text.contentEquals("0")) {
      		  response = "END ";
        }else if(text.contentEquals("1*" + textArray[1])){
        	response = "CON 1. Press 1 to vote for the motion\n"
    				+ "2. Press 2 to vote against the motion"; 
        }else if(text.contentEquals("1*" + textArray[1] + "*1") || text.contentEquals("1*" + textArray[1] + "*2")) {
        	response = "CON Give a reason for your chosen option\n"; 
        }else if(text.contentEquals("1*" + textArray[1] + "*1*" + textArray[3]) 
        		|| text.contentEquals("1*" + textArray[1] + "*2*" + textArray[3])) {
        	//save data to db [currently only saves into an array]
        	data = new String[textArray.length];
        	data[0] = phoneNumber;
        	data[1] = textArray[1]; //user's name
        	data[2] = textArray[2]; //answer for poll :: 1 for FOR, 2 for AGAINST
        	data[3] = textArray[3]; //reason for answer
        	
        	//end session
        	response = "END Thank you " + textArray[1] + " for partaking in the poll.\n"
        			+ "We appreciate!";
        }
        
        return response;
    }

}

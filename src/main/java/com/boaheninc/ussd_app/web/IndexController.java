package com.boaheninc.ussd_app.web;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boaheninc.ussd_app.model.UssdParameters;

@RestController
@RequestMapping("/api")
public class IndexController {
    
	@PostMapping(value="/ussd/random-poll", consumes = {"application/x-www-form-urlencoded"})
    public String index(UssdParameters params) {
    	String response = "", sessionId, text, phoneNumber, serviceCode;
    	String[] textArray;
    	String[] data;
    	
    	sessionId = params.getSessionId();
    	text = params.getText();
    	phoneNumber = params.getPhoneNumber();
    	serviceCode = params.getServiceCode();
    	
    	/*
    	 * extract individual strings from text that are concatenated by *
    	 * Assumptions are neither of these parameters will be null during the 
    	 * initiation of the USSD call hence no checks for null in the code
    	 */
    	
    	textArray = text.split("\\*");
    	int separatorOccurance = StringUtils.countOccurrencesOf(text, "*");
        if(text == "") {
    		response = "CON Welcome to the DISSOLVE GFA Polls\nPress 1 to Continue to Polls"
    				+ "\nPress 0 to exit Polls";
        }else if(text.contentEquals("1")) {
      		  response = "CON Please provide your name\n"; 
        }else if(text.contentEquals("0")) {
      		  response = "END";
        }else if(separatorOccurance==1){
        	response = "CON Press 1 to vote for the motion"
    				+ "\nPress 2 to vote against the motion"; 
        }else if(separatorOccurance==2) {
        	response = "CON give a reason for your chosen option\n"; 
        }else if(separatorOccurance==3) {
        	//save data to db [currently only saves into an array]
        	data = new String[textArray.length];
        	data[0] = phoneNumber;
        	data[1] = textArray[1]; //user's name
        	data[2] = textArray[2]; //answer for poll :: 1 for FOR, 2 for AGAINST
        	data[3] = textArray[3]; //reason for answer
        	
        	//end session
        	response = "END Thank you " + textArray[1] + " for partaking in the poll.\nWe appreciate!";
        }
        
        return response;
    }

}

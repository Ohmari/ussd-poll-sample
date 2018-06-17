package com.boaheninc.ussd_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UssdParameters {

	private String sessionId;
	private String text;
	private String phoneNumber;
	private String serviceCode;
}

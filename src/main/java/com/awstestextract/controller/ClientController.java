package com.awstestextract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awstestextract.aws.AwsS3Client;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ClientController {

	@Autowired
	private AwsS3Client awsS3Client;

	@GetMapping("client4")
	public String getFileFromBucket4() throws JsonProcessingException {
		return this.awsS3Client.getFileFromBucket4("demobuckettextract", "Textract-Blog-6.png");
	}

}

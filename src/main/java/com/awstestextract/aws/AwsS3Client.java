package com.awstestextract.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.AnalyzeDocumentRequest;
import com.amazonaws.services.textract.model.AnalyzeDocumentResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.services.textract.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AwsS3Client {

	@Value("${aws.credentials.access-key}")
	private String awsAccessKey;

	@Value("${aws.credentials.secret-key}")
	private String awsSecretKey;

	@Autowired
	private AmazonS3 amazonS3;

	@Bean
	public AmazonS3 amazonS3() {
		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	}

	public String getFileFromBucket4(String bucketName, String key) throws JsonProcessingException {

		AmazonTextractClientBuilder clientBuilder = AmazonTextractClientBuilder.standard()
				.withRegion(Regions.US_EAST_1);
		clientBuilder.setCredentials(new AWSStaticCredentialsProvider(
				new BasicAWSCredentials("AKIAV42HWE#####SHB", "bgu9#####Jcz/ts2u/8V#####jZYzc8")));

		String document = "Textract-Blog-6.png";
		String bucket = "demobuckettextract";

		AmazonTextract client = clientBuilder.build();

		AnalyzeDocumentRequest request = new AnalyzeDocumentRequest().withFeatureTypes("TABLES")
				.withDocument(new Document().withS3Object(new S3Object().withName(key).withBucket(bucketName)));

		AnalyzeDocumentResult result = client.analyzeDocument(request);
		String json = new ObjectMapper().writeValueAsString(result);

		return json;
	}

}

package com.loan_craft.upload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class UploadApplication {

	public static void main(String[] args) {
		System.out.println("GOOGLE_APPLICATION_CREDENTIALS: " + System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
		try{
			String path = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
			if (path == null) {
				System.out.println("GOOGLE_APPLICATION_CREDENTIALS not set");
				return;
			}else {
				Path filePath = Paths.get(path);
				if (Files.exists(filePath)) {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode json = mapper.readTree(Files.readString(filePath));
					String clientEmail = json.get("client_email").asText();
					System.out.println("Google Credentials client_email: " + clientEmail);
				} else {
					System.out.println("File not found at: " + path);
				}
			}
		}
		catch(Exception e){
			System.out.println("exception occured");
			System.out.println(e.getMessage());
		}

		SpringApplication.run(UploadApplication.class, args);
	}

}

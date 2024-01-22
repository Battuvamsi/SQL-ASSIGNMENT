package com.redis.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.application.serviceimp.SqlService;

@RestController
public class Sqlcontroller {
	
		@Autowired
		SqlService sqlservice;
	
	
	   @PostMapping("/execute")
	   public void executeQuery(@RequestBody String requestBody) {
		    try {
		        ObjectMapper objectMapper = new ObjectMapper();
		        JsonNode jsonNode = objectMapper.readTree(requestBody);
		        
		        if (jsonNode.has("query")) {
		            String query = jsonNode.get("query").asText();
		            processQuery(query);
		        } else {
		            System.out.println("Invalid request format. Please provide a 'query' property.");
		        }
		    } catch (Exception e) {
		        System.out.println("Error processing the request: " + e.getMessage());
		    }
		}

		private void processQuery(String query) {
		    if (query.toUpperCase().startsWith("CREATE TABLE")) {
		        sqlservice.createTable(query);
		    } else if (query.toUpperCase().startsWith("INSERT INTO")) {
		        sqlservice.insertIntoTable(query);
		    } else {
		        System.out.println("Unsupported query. Please enter valid SQL-like queries.");
		    }
		}

}

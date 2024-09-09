package petStoreApi;

import io.restassured.RestAssured;

public class TestConfig {

	 static {
	        String baseUri = System.getProperty("api.baseUri", "http://localhost:8080");
	        String basePath = System.getProperty("api.basePath", "/api/v3");

	        RestAssured.baseURI = baseUri;
	        RestAssured.basePath = basePath;
	    }
	
	
}

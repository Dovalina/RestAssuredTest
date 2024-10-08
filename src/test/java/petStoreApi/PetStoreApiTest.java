package petStoreApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import java.io.File;




public class PetStoreApiTest extends TestConfig {


	@Test(priority = 1)
    public void testCreatePet() {
        String requestBody = "{ \"id\": 101, \"name\": \"Mascota Sergio\", \"status\": \"available\" }";

        Response response = given()
            .body(requestBody)
            .header("Content-Type", "application/json")
        .when()
            .post("/pet");

        response.then()
            .statusCode(200)
            .body("name", equalTo("Mascota Sergio"))
            .body("id", equalTo(101))
            .body("status", equalTo("available"));

        // Obtener y mostrar la información de la mascota creada
        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");

        System.out.println("Mascota creada con ID: " + id + ", Nombre: " + name + ", Estado: " + status);
    }

	@Test(priority = 2)
    public void testGetPetById() {
        int petId = 101; // Usar el ID de la mascota creada en la prueba anterior

        Response response = given()
            .pathParam("petId", petId)
        .when()
            .get("/pet/{petId}");

        response.then()
            .statusCode(200)
            .body("id", equalTo(petId));

        // Obtener y mostrar la información de la mascota consultada
        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");

        System.out.println("Mascota consultada con ID: " + id + ", Nombre: " + name + ", Estado: " + status);
    }

	@Test(priority = 3)
    public void testUpdatePet() {
        int petId = 101; // Usar el ID de la mascota creada en la prueba anterior
        String requestBody = "{ \"id\": " + petId + ", \"name\": \"Mascota Sergio Actualizada\", \"status\": \"sold\" }";

        Response response = given()
            .body(requestBody)
            .header("Content-Type", "application/json")
        .when()
            .put("/pet");

        response.then()
            .statusCode(200)
            .body("name", equalTo("Mascota Sergio Actualizada"))
            .body("status", equalTo("sold"));

        // Obtener y mostrar la información actualizada de la mascota
        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");

        System.out.println("Mascota actualizada con ID: " + id + ", Nuevo Nombre: " + name + ", Nuevo Estado: " + status);
    }

	@Test(priority = 4)
    public void testGetUpdatedPetById() {
        int petId = 101; // Usar el ID de la mascota actualizada

        Response response = given()
            .pathParam("petId", petId)
        .when()
            .get("/pet/{petId}");
        response.then()
            .statusCode(200)
            .body("id", equalTo(petId));

        // Obtener y mostrar la información actualizada de la mascota
        int id = response.jsonPath().getInt("id");
        String name = response.jsonPath().getString("name");
        String status = response.jsonPath().getString("status");

        System.out.println("Mascota después de la actualización con ID: " + id + ", Nombre: " + name + ", Estado: " + status);
    }
    
	 @Test(priority = 5)
	    public void placeOrder() {
	      
	        String orderJson = "{\n" +
	                           "  \"id\": 100,\n" +
	                           "  \"petId\": 101,\n" +
	                           "  \"quantity\": 2,\n" +
	                           "  \"shipDate\": \"2024-09-05T00:00:00Z\",\n" +
	                           "  \"status\": \"placed\",\n" +
	                           "  \"complete\": true\n" +
	                           "}";

	        
	        Response response = RestAssured
	            .given()
	            .contentType(ContentType.JSON)
	            .body(orderJson)
	            .post("/store/order");
	        response.then().statusCode(200);
	        response.then().body("status", equalTo("placed"));

	      
	        int orderId = response.jsonPath().getInt("id");
	        int petId = response.jsonPath().getInt("petId");
	        int quantity = response.jsonPath().getInt("quantity");
	        Response petResponse = RestAssured
	            .given()
	            .get("/pet/" + petId);

	        //seimprime order id+petid+nombrepet+cantidad
	        String petName = petResponse.jsonPath().getString("name");
	        System.out.println("Order ID: " + orderId +"  "+ "Pet ID: " + petId+"  "+ "Pet Name: " + petName+"  "+ "Quantity Ordered: " + quantity);

	    }
	
	 @Test(priority = 11)
	 
	
	 public void deleteOrderById() {
	     int orderId = 100; // Asume que este ID existe en la base de datos

	     Response response = RestAssured
	         .given()
	         .delete("/store/order/" + orderId);

	     response.then().statusCode(200);
	 }

	 
	 
	 @Test(priority = 12)
    public void testDeletePet() {
        int petId = 101; 
        given()
            .pathParam("petId", petId)
        .when()
            .delete("/pet/{petId}")
        .then()
            .statusCode(200);

        // Intentar consultar la mascota para verificar que ha sido eliminada
        Response response = given()
            .pathParam("petId", petId)
        .when()
            .get("/pet/{petId}");

        response.then()
            .statusCode(404); // Verificar que el ID no se encuentra

        // Imprimir un mensaje indicando que la mascota ha sido eliminada
        System.out.println("La mascota con ID: " + petId + " ha sido eliminada.");
    }

	@Test(priority = 6)
    public void testFindPetsByStatus() {
        given()
            .queryParam("status", "available")
        .when()
            .get("/pet/findByStatus")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0)); // Verificar que hay al menos una mascota con el estado "available"
    }

	@Test(priority = 7)
    public void findOrderById() {
        int orderId = 100; 
        Response orderResponse = RestAssured
            .given()
            .get("/store/order/" + orderId);
        orderResponse.then().statusCode(200);

        int petId = orderResponse.jsonPath().getInt("petId");
        int quantity = orderResponse.jsonPath().getInt("quantity");

        
        Response petResponse = RestAssured
            .given()
            .get("/pet/" + petId);
        petResponse.then().statusCode(200);
        String petName = petResponse.jsonPath().getString("name");
        System.out.println("Order ID: " + orderId +"  "+ "Pet ID: " + petId+"  "+ "Pet Name: " + petName+ "  "+ "Quantity Ordered: " + quantity);

    }

	@Test(priority = 8)
    public void createUser() {
        // JSON del usuario para la solicitud POST
        String userJson = "{\n" +
                          "  \"id\": 100,\n" +
                          "  \"username\": \"SergioUsr\",\n" +
                          "  \"firstName\": \"Sergio\",\n" +
                          "  \"lastName\": \"Dovalina\",\n" +
                          "  \"email\": \"sergio@dova.com\",\n" +
                          "  \"password\": \"Pasword\",\n" +
                          "  \"phone\": \"8711289095\",\n" +
                          "  \"userStatus\": 1\n" +
                          "}";

        // se envia POST para crear un nuevo usuario
        Response response = RestAssured
            .given()
            .contentType(ContentType.JSON)
            .body(userJson)
            .post("/user");
        response.then().statusCode(200);
        response.then().body("username", equalTo("SergioUsr"));

        // Extraer detalles de la respuesta
        int userId = response.jsonPath().getInt("id");
        String firstName = response.jsonPath().getString("firstName");
        String password = response.jsonPath().getString("password");

        // Imprimir la leyenda con los detalles del usuario
        System.out.println("El usuario ha sido creado con éxito.");
        System.out.println("ID: " + userId);
        System.out.println("First Name: " + firstName);
        System.out.println("Password: " + password);
    }
    
    
	
	@Test(priority = 10)
    public void deleteUser() {
        String username = "SergioUpdatedUsr"; 

       
        Response response = RestAssured
            .given()
            .delete("/user/" + username);

     
        response.then().statusCode(200);

    // println para saber que usuario fue eliminado
        System.out.println("El usuario ha sido eliminado con éxito.");
        System.out.println("Username: " + username);
        int userId = 1;
        System.out.println("ID del usuario eliminado: " + userId);
    }
    
    
	

}

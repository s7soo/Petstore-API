package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.core.net.Priority;
import org.testng.annotations.*;

import static constants.Urls.baseUrl;
import static constants.Values.logger;
import static io.restassured.RestAssured.*;
import static utilities.GenericHelper.logPrint;
import static utilities.GenericHelper.verifyStatusCode;
import static utilities.StoreHelper.getStoreRequestBody;

public class StoreTest {

    int globalOrderId;
    @BeforeClass
    public static void setup(){
        // assign base url
        RestAssured.baseURI = baseUrl;
    }
    @Test (priority = 3)
    public void showStoreInventory(){
        Response response =
                given()
                        .when()
                        .get("store/inventory")
                        .then()
                        .extract()
                        .response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Getting Store Inventory...");
        response.body().prettyPrint();
    }


    @Test (priority = 1)
    public void createNewOrder(){
        Response response =
                given()
                        .header("accept","application/json")
                        .contentType("application/json")
                        .body(getStoreRequestBody())
                        .when()
                        .post("store/order")
                        .then()
                        .extract()
                        .response();
        logPrint(response, "Creating new order...");
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
        globalOrderId = response.path("id");
    }

    @Test (priority = 3)
    public void showOrderById(){
        int orderId = globalOrderId;
        Response response =
                given()
                        .when()
                        .get("store/order/"+orderId)
                        .then()
                        .extract()
                        .response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Getting order by Id...");
        response.body().prettyPrint();
    }
    @Test (priority = 4)
    public void deleteOrderById(){
        int orderId = globalOrderId;
        Response response =
                given()
                        .when()
                        .delete("store/order/"+orderId)
                        .then()
                        .extract()
                        .response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "deleting order by Id...");
        response.body().prettyPrint();
    }
    @AfterClass
    public static void tearDown(){
        // assign base url
        RestAssured.delete();
    }
}

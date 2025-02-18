package tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Logger;


import static constants.Urls.baseUrl;
import static constants.Values.petStatus;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static utilities.PetHelper.getRequestBody;

public class PetTest {
    public static final Logger logger = Logger.getLogger(String.valueOf(PetTest.class));

    @BeforeClass
    public static void setup(){
        // assign base url
        RestAssured.baseURI = baseUrl;
    }

    @Test
    public void getAvailablePets(){
        String status = petStatus[0];
        // get request response
        Response response = RestAssured.get("pet/findByStatus?status="+status);
        // verify if response is success
        response.then().assertThat().statusCode(200);
        // verify body status value
        response.then().assertThat().body("[0].status", equalTo(status));
    }

    @Test
    public void getPendingPets(){
        String status = petStatus[1];
        // get request response
        Response response = RestAssured.get("pet/findByStatus?status="+status);
        // verify if response is success
        response.then().assertThat().statusCode(200);
        // verify body status value
        response.then().assertThat().body("[0].status", equalTo(status));
    }
    @Test
    public void getSoldPets(){
        String status = petStatus[2];
        // get request response
        Response response = RestAssured.get("pet/findByStatus?status="+status);
        // verify if response is success
        response.then().assertThat().statusCode(200);
        // verify body status value
        response.then().assertThat().body("[0].status", equalTo(status));
    }

    @Test
    public void postNewPet(){
        int id = 11616;
        String name = "CatFish";
        String photo = "";
        String status = petStatus[0];
        Response response =
        given().
                header("Content-Type", "application/json").
                body(getRequestBody(
                        id,
                        name,
                        photo,
                        status
                )).
                when().
                post("/pet").
                then().
                statusCode(200).
                body("name",equalTo(name)).
                body("status",equalTo(status)).
                extract().
                response();

    }
}

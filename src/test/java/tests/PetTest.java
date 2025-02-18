package tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;


import static constants.Urls.baseUrl;
import static constants.Values.*;
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
        logger.info(String.valueOf(response.getStatusCode()));
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
        logger.info(String.valueOf(response.getStatusCode()));
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
        logger.info(String.valueOf(response.getStatusCode()));
    }
    @Test
    public void getPetById(){
        int id = 11616;
        Response response =
                given().
                        header("accept", "application/json").
                        header("api_key", apiKey).
                        when().
                        get("/pet/"+id).
                        then().
                        statusCode(200).
                        extract().
                        response();
    }

    @Test
    public void createNewPet(){
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
        logger.info(String.valueOf(response.getStatusCode()));

    }

    @Test
    public void updatePetPhoto(){
        createNewPet();
        int id = 11616;
        File photo = new File(imgUrl);
        Response response =
                given().
                        header("accept", "application/json").
                        contentType("multipart/form-data").
                        multiPart("file", photo, "image/png").
                        when().
                        post("/pet/"+id+"/uploadImage").
                        then().
                        statusCode(200).
                        extract().
                        response();
        logger.info(String.valueOf(response.getStatusCode()));
    }
    @Test
    public void updatePetDataUsingId(){
        int id = 11616;
        String name = "CatFishhh";
        String status = petStatus[0];
        Response response =
                given().
                        header("accept", "application/json").
                        contentType("application/x-www-form-urlencoded").
                        formParam("name",name).
                        formParam("status",status).
                        when().
                        post("/pet/"+id).
                        then().
                        statusCode(200).
                        body("message", equalTo(String.valueOf(id))).
                        extract().response();
        logger.info(String.valueOf(response.getStatusCode()));
    }

    @Test
    public void updatePet(){
        createNewPet();
        int id = 11616;
        String name = "CatFish";
        String photo = "";
        String status = petStatus[2];

        Response response =
                given().
                        header("content-type","application/json").
                        body(getRequestBody(
                                id,
                                name,
                                photo,
                                status
                        )).
                        when().
                        put("/pet").
                        then().
                        statusCode(200).
                        body("status",equalTo(petStatus[2])).
                        extract().
                        response();
        logger.info(String.valueOf(response.getStatusCode()));
    }

    @Test
    public void deletePet(){
        int id = 11616;

        Response response =
                given().
                        header("accept", "application/json").
                        header("api_key", apiKey).
                        when().
                        delete("/pet/"+id).
                        then().
                        statusCode(200).
                        extract().
                        response();
    }

}

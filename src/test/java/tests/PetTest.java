package tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;


import java.io.File;


import static constants.Urls.baseUrl;
import static constants.Values.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


import static utilities.GenericHelper.*;
import static utilities.PetHelper.*;

public class PetTest {

    @BeforeClass
    public static void setup(){
        // assign base url
        RestAssured.baseURI = baseUrl;
    }

    @Test (priority = 3)
    public void getAvailablePets(){
        String status = petStatus[0];
        // get request response
        Response response = RestAssured.get("pet/findByStatus?status="+status);
        // verify if response is success
        response.then().assertThat().statusCode(200);
        // verify body status value
        response.then().assertThat().body("[0].status", equalTo(status));
        logPrint(response, "Getting Available Pet");
    }

    @Test (priority = 3)
    public void getPendingPets(){
        String status = petStatus[1];
        // get request response
        Response response = RestAssured.get("pet/findByStatus?status="+status);
        // verify if response is success
        response.then().assertThat().statusCode(200);
        // verify body status value
        response.then().assertThat().body("[0].status", equalTo(status));
        logPrint(response, "Getting Pending Pet");
    }
    @Test (priority = 3)
    public void getSoldPets(){
        String status = petStatus[2];
        // get request response
        Response response = RestAssured.get("pet/findByStatus?status="+status);
        // verify if response is success
        response.then().assertThat().statusCode(200);
        // verify body status value
        response.then().assertThat().body("[0].status", equalTo(status));

        logPrint(response, "Getting Sold Pet");
    }
    @Test (priority = 3)
    public void getPetById(){
        createNewPet();
        int id = 11616;
        Response response =
                given().
                        header("accept", "application/json").
                        header("api_key", apiKey).
                        when().
                        get("/pet/"+id).
                        then().
                        extract().
                        response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Getting Pet...");
    }

    @Test (priority = 1)
    public void createNewPet(){
        int id = 11616;
        String name = "CatFish";
        String photo = "";
        String status = petStatus[0];
        Response response =
        given().
                header("Content-Type", "application/json").
                body(getPetRequestBody(
                        id,
                        name,
                        photo,
                        status
                )).
                when().
                post("/pet").
                then().
                body("name",equalTo(name)).
                body("status",equalTo(status)).
                extract().
                response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Creating New Pet...");
    }

    @Test (priority = 2)
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
                        extract().
                        response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Updating Pet Photo...");
    }
    @Test (priority = 2)
    public void updatePetDataUsingId(){
        createNewPet();
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
                        body("message", equalTo(String.valueOf(id))).
                        extract().response();

        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Updating a Pet using Form Param...");
    }

    @Test (priority = 2)
    public void updatePet(){
        createNewPet();
        int id = 11616;
        String name = "CatFish";
        String photo = "";
        String status = petStatus[2];

        Response response =
                given().
                        header("content-type","application/json").
                        body(getPetRequestBody(
                                id,
                                name,
                                photo,
                                status
                        )).
                        when().
                        put("/pet").
                        then().
                        body("status",equalTo(petStatus[2])).
                        extract().
                        response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Updating a Pet using Body...");
    }

    @Test (priority = 4)
    public void deletePet(){
        int id = 11616;

        Response response =
                given().
                        header("accept", "application/json").
                        header("api_key", 452).
                        when().
                        delete("/pet/"+id).
                        then().
                        extract().
                        response();
        verifyStatusCode(200, response.statusCode());
        logPrint(response, "Deleting a Pet...");
    }
    @AfterClass
    public static void tearDown(){
        // assign base url
        RestAssured.delete();
    }

}

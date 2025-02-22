package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static constants.Urls.baseUrl;
import static io.restassured.RestAssured.given;
import static utilities.GenericHelper.logPrint;
import static utilities.GenericHelper.verifyStatusCode;

import static utilities.UserHelper.*;

public class UserTest {

    @BeforeClass
    public static void setup(){
        // assign base url
        RestAssured.baseURI = baseUrl;
    }

    @Test
    public void createNewListOfUsers(){
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(
                                "[\n" +
                                        getUserBody(
                                        1,"mohuss","mo",
                                        "huss","mo@gmail.com",
                                        "13355","2115446",0)
                                        +
                                        ","+getUserBody(
                                        2,"mohuss","mo",
                                        "huss","mo@gmail.com",
                                        "13355","2115446",0)
                                        +
                                        "\n]"
                        )
                        .when()
                        .post("user/createWithList")
                        .then()
                        .extract()
                        .response();
        logPrint(response, "Creating new user...");
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }
    @Test
    public void createNewUser(){
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(getUserBody(
                                1,"mohibuss","mo",
                                "huss","mo@gmail.com",
                                "13355","2115446",0)
                        )
                        .when()
                        .post("user")
                        .then()
                        .extract()
                        .response();
        logPrint(response, "Creating new user...");
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }

    @Test
    public void getUserByUsername(){
        String username = "mohuss";
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .when()
                        .get("user/"+username)
                        .then()
                        .extract()
                        .response();
        logPrint(response, "showing user "+username);
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }

    @Test
    public void updateUserByUsername(){
        String username = "mohuss";
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(
                                getUserBody(
                                        1,"mohuss","aho",
                                        "moli","ahomolio@gmail.com",
                                        "13355","2115446",0)
                        )
                        .when()
                        .put("user/"+username)
                        .then()
                        .extract()
                        .response();
        logPrint(response, "updating user "+username);
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }

    @Test
    public void deleteUserByUsername(){
        String username = "mohuss";
        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .when()
                        .delete("user/"+username)
                        .then()
                        .extract()
                        .response();
        logPrint(response, "deleting user "+username);
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }


    @Test
    public void userLogin(){
        String username = "mohuss";
        String password = "13355";
        Response response =
                given()
                        .formParam("username",username)
                        .formParam("password", password)
                        .when()
                        .get("user/login")
                        .then()
                        .extract()
                        .response();
        logPrint(response, "login with user "+username);
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }

    @Test
    public void userLogout(){

        Response response =
                given()
                        .when()
                        .get("user/logout")
                        .then()
                        .extract()
                        .response();
        logPrint(response, "user logout... ");
        response.body().prettyPrint();
        verifyStatusCode(200, response.statusCode());
    }
}

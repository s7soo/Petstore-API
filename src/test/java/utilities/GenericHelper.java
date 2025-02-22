package utilities;

import io.restassured.response.Response;
import org.junit.Assert;
import tests.PetTest;

import java.util.logging.Logger;

import static constants.Values.logger;

public class GenericHelper {

    public static void logPrint(Response response, String msg){
        int status = response.getStatusCode();
        if (status == 200)
            logger.info(msg +"\nStatus Code: "+ status);
        else
            logger.info(getStatusCodeMessage(status));

    }
    public static String getStatusCodeMessage(int statusCode) {
        return switch (statusCode) {
            case 200 -> "200 OK: The request was successful.";
            case 201 -> "201 Created: The request was successful and a new resource was created.";
            case 204 -> "204 No Content: The request was successful, but there is no content to return.";
            case 400 -> "400 Bad Request: The server could not understand the request.";
            case 401 -> "401 Unauthorized: Authentication is required and has failed or has not yet been provided.";
            case 403 -> "403 Forbidden: The client does not have permission to access the resource.";
            case 404 -> "404 Not Found: The requested resource could not be found.";
            case 405 -> "405 Method Not Allowed: The method specified in the request is not allowed for the resource.";
            case 409 ->
                    "409 Conflict: The request could not be completed due to a conflict with the current state of the resource.";
            case 500 ->
                    "500 Internal Server Error: The server encountered an unexpected condition that prevented it from fulfilling the request.";
            case 501 ->
                    "501 Not Implemented: The server does not support the functionality required to fulfill the request.";
            case 502 -> "502 Bad Gateway: The server received an invalid response from the upstream server.";
            case 503 -> "503 Service Unavailable: The server is temporarily unavailable.";
            default -> "Unknown Status Code: The status code provided is not recognized.";
        };
    }

    public static void verifyStatusCode(int expectedCode, int actualCode){
        Assert.assertEquals(getStatusCodeMessage(actualCode),expectedCode, actualCode);
    }
}

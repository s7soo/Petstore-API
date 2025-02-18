package utilities;

public class PetHelper {
    public static String getRequestBody(int id, String name, String photo, String status){
        if (name.isEmpty())
            name = "name";
        if (photo.isEmpty())
            photo = "string";
        if (status.isEmpty())
            status = "available";
        String requestBody = "{\n" +
                "    \"id\": "+id+",\n" +
                "    \"category\": {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \""+photo+"\"\n" +
                "    },\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"photoUrls\": [\"string\"],\n" +
                "    \"tags\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"string\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"status\": \""+status+"\"\n" +
                "}";

        return requestBody;
    }
}

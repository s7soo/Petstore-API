package utilities;

public class StoreHelper {
    public static String getStoreRequestBody(){
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"petId\": 1,\n" +
                "  \"quantity\": 10,\n" +
                "  \"shipDate\": \"2025-02-22T16:11:58.331Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";
    }
}

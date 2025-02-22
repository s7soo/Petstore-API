package constants;

import tests.PetTest;

import java.util.logging.Logger;

public class Values {
    public static final Logger logger = Logger.getLogger(String.valueOf(Values.class));
    public static String[] petStatus = {"available", "pending", "sold"};
    public static String imgUrl = "src/main/resources/images/pet.png";

    public static String apiKey = "12345678";
}

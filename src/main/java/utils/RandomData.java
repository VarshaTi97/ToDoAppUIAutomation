package utils;

import com.github.javafaker.Faker;

import java.util.UUID;

public class RandomData {

    public static Faker faker = new Faker();

//    public static String getRandomDataFor(RandomDataTypeNames dataTypeNames) {
//        switch (dataTypeNames) {
//            case FIRSTNAME:
//                return faker.name().firstName();
//            case LASTNAME:
//                return faker.name().lastName();
//            case FULLNAME:
//                return faker.name().fullName();
//            case COUNTRY:
//                return faker.address().country();
//            case CITYNAME:
//                return faker.address().cityName();
//            default:
//                return "";
//        }
//    }

    public static String getItemWithSpaces(){
        return "  " +faker.name().firstName()+ "  ";
    }

    public static String getItemName(){
        return faker.name().firstName();
    }

    public static Object[] getListOfRandomItems(){
        Object[] data = new Object[3];
        int j = 0;
        data[j++] = faker.name().firstName();
        data[j++] = faker.number().digits(10);
        data[j++] = faker.regexify("[    ][a-z][A-Z][1-9][ ][*%$#-@!^+=,/][   ]");
        return data;
    }

}

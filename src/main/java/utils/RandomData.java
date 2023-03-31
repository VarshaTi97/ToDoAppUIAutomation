package utils;

import com.github.javafaker.Faker;

//utility class to generate random data
public class RandomData {

    public static Faker faker = new Faker();

    //get random data with surrounding spaces
    public static String getItemWithSpaces(){
        return "  " +faker.name().firstName()+ "  ";
    }

    //get random todoitem
    public static String getItemName(){
        return faker.name().firstName();
    }

    //get list of random data
    public static Object[] getListOfRandomItems(){
        Object[] data = new Object[3];
        int j = 0;
        data[j++] = faker.name().firstName();
        data[j++] = faker.number().digits(10);
        data[j++] = faker.regexify("[    ][a-z][A-Z][1-9][ ][*%$#-@!^+=,/][   ]");
        return data;
    }

    //get list of random data with count specified by user
    public static Object[] getListOfRandomItems(int length){
        Object[] data = new Object[length];
        int j = 0;
        data[j++] = faker.name().firstName();
        data[j++] = faker.number().digits(10);
        for(int i=j;i<length;i++)
            data[j++] = faker.regexify("[    ][a-z][A-Z][1-9][ ][*%$#-@!^+=,/][   ][A-Z][a-z][0-9]");
        return data;
    }

}

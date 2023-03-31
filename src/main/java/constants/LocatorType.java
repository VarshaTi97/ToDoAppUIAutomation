package constants;

//Defines constants for locator types to be used in property file
public class LocatorType {
    private LocatorType(){}
    public static final String XPATH = "xpath";
    public static final String ID= "id";
    public static final String CSS_SELECTOR = "css";
    public static final String CLASS_NAME = "className";
    public static final String TAG_NAME = "tagName";
    public static final String LINK_TEXT = "linkText";
    public static final String PARTIAL_LINK_TEXT = "partialLinkText";
}

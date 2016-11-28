/**
 * Created by mnoe on 28.11.2016.
 */
public class ReverseString {

    public static String reverseString(String input){
        if(input.length() > 1) {
            return reverseString(input.substring(1,input.length())) + input.charAt(0);
        } else {
            return input;
        }
    }
}

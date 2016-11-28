import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.BaseMatcher.*;
/**
 * Created by mnoe on 28.11.2016.
 */

@Test
public class TestReverseString {

    public void testReverseStringReturnsString(){
        String output = ReverseString.reverseString("TEST");
        assertThat(output, is(not(nullValue())));
        assertThat(output, instanceOf(String.class));
    }

    public void testReverseStringReturnsReverse(){
         String output = ReverseString.reverseString("TEST");
        assertThat(output, is("TSET"));
    }

}

import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;
import org.testng.internal.annotations.ExpectedExceptionsAnnotation;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by marcel on 01.12.2016.
 */

@Test
public class MarcelListUnitTest {

    private static final String VALUE1 = "TEST";
    private static final String VALUE2 = "BAR";
    private static final String VALUE3 = "FOOBAR";

    public void testPutOneEntry() {
        prepareListWithOneEntry();
    }

    private MarcelList<String> prepareListWithOneEntry() {
        MarcelList<String> list = new MarcelList<>();
        String value = new String(VALUE1);
        list.put(value);
        return list;
    }

    public void testPutTwoEntries() {
        prepareListWithTwoEntries();
    }

    private MarcelList<String> prepareListWithTwoEntries() {
        MarcelList<String> list = new MarcelList<>();
        String value1 = new String(VALUE1);
        String value2 = new String(VALUE2);
        list.put(value1);
        list.put(value2);
        return list;
    }

    public void testPutThreeEntries() {
        prepareListWithThreeEntries();
    }

    private MarcelList<String> prepareListWithThreeEntries() {
        MarcelList<String> list = new MarcelList<>();
        String value1 = new String(VALUE1);
        String value2 = new String(VALUE2);
        String value3 = new String(VALUE3);
        list.put(value1);
        list.put(value2);
        list.put(value3);
        return list;
    }

    public void testPuttedSingleValueCanBeRetrieved(){
        MarcelList<String> list = prepareListWithTwoEntries();
        String retrieved = list.get(0);
        assertThat(retrieved, is(VALUE1));
    }

    public void testSecondEntryCanBeRetrieved(){
        MarcelList<String> list = prepareListWithTwoEntries();
        String retrieved = list.get(1);
        assertThat(retrieved, is(VALUE2));
    }

    public void testThirdEntryCanBeRetrieved(){
        MarcelList<String> list = prepareListWithThreeEntries();
        String retrieved = list.get(2);
        assertThat(retrieved, is(VALUE3));
    }

    public void testAllEntriesCanBeRetrievedFromListWithThreeEntries(){
        MarcelList<String> list = prepareListWithThreeEntries();
        String retrieved1 = list.get(0);
        String retrieved2 = list.get(1);
        String retrieved3 = list.get(2);
        assertThat(retrieved1, is(VALUE1));
        assertThat(retrieved2, is(VALUE2));
        assertThat(retrieved3, is(VALUE3));
    }

    public void testGenericFunctionality(){
        class Superclass {
            public String getValue(){
                return VALUE1;
            }
        }

        class IntermediateClass extends Superclass {
            @Override
            public String getValue() {
                return VALUE2;
            }
        }

        class DerivedClass extends IntermediateClass {
            @Override
            public String getValue() {
                return VALUE3;
            }
        }

        Superclass superObject = new Superclass();
        IntermediateClass intermediateObject = new IntermediateClass();
        DerivedClass derivedClass = new DerivedClass();

        MarcelList<Superclass> list = new MarcelList<>();
        list.put(superObject);
        list.put(intermediateObject);
        list.put(derivedClass);

        Superclass object1 = list.get(0);
        Superclass object2 = list.get(1);
        Superclass object3 = list.get(2);

        assertThat(object1.getValue(), is(VALUE1));
        assertThat(object2.getValue(), is(VALUE2));
        assertThat(object3.getValue(), is(VALUE3));
    }

    @Test(expectedExceptions=NoSuchElementException.class)
    public void testGetOnEmptyListThrowsNoSuchElementException(){
        MarcelList<String> list = new MarcelList<>();
        list.get(0);
    }

    @Test(expectedExceptions=NoSuchElementException.class)
    public void testGetOfNonExistingElementOnNonEmptyListThrowsNoSuchElementException(){
        MarcelList<String> list = new MarcelList<>();
        list.put(new String());
        list.get(1);
    }

    @Test(expectedExceptions=NoSuchElementException.class)
    public void testGetOnNonExistingElementOfLongerListThrowsNoSuchElementException(){
        MarcelList<String> list = prepareListWithThreeEntries();
        list.get(3);
    }
}

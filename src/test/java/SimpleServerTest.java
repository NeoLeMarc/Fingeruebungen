import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by mnoe on 30.11.2016.
 */

@Test
public class SimpleServerTest {

    public void testSimpleServer() {
        System.out.println("Entering simple server test");
        try {
            SimpleServer server = new SimpleServer(9090);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exiting simple server test");
    }
}

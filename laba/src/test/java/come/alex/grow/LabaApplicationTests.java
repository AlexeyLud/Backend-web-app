package come.alex.grow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class LabaApplicationTests {

    @Before
    public void before(){
        System.out.println("Before");
    }

    @Test
    public void test(){
        int a = 7;
        assertEquals(34,34);
        assertTrue(true);
        assertNotNull(a);
        System.out.println("Test working correct!");
    }

    @After
    public void after(){
        System.out.println("After");
    }

}

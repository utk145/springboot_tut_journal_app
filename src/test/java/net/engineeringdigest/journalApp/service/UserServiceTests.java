package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {


    @Autowired
    private UserRepository userRepository;
    /*
        Without the SpringBootTest, you will get java.lang.NullPointerException: Cannot invoke "net.engineeringdigest.journalApp.repository.UserRepository.findByUserName(String)" because "this.userRepository" is null.

       This is because without the spring application context, no beans will be created for the @Component s.
     */


    @Disabled
    @Test
    public void testSomethingJLT() {
        assertEquals(4, 2 + 2);
    }

    @Disabled
    @Test
    public void testFindByUserName() {
        assertEquals(4, 2 + 2);
        assertNotNull(userRepository.findByUserName("raj"));
    }


    @Disabled
    @Test
    public void testGreaterAThanB(int a, int b) {
        assertTrue(a > b);
    }


    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,1"
    })
    public void test2(int a, int b, int expected) {
        assertEquals(
                expected,
                a + b,
                "failed for a: " + a + " and b: " + b
        );
    }

}

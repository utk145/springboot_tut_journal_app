package net.engineeringdigest.journalApp.repos;


import net.engineeringdigest.journalApp.repository.UserRepositoryImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplementationTests {


    @Autowired
    private UserRepositoryImplementation userRepositoryImplementation;

    @Test
    public void justTesting() {
//        userRepositoryImplementation.getUsersForSentimentAnalysis();
        Assertions.assertNotNull(userRepositoryImplementation.getUsersForSentimentAnalysis());
    }


}

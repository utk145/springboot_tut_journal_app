package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository // or use @Component, it should be a bean object. Once you do this, Spring will be able to find UserRepositoryImplementation and inject it into your test.
public class UserRepositoryImplementation {


    @Autowired
    private MongoTemplate mongoTemplate;


    public List<UserEntity> getUsersForSentimentAnalysis() {

        Query query = new Query();
//        query.addCriteria(
//                Criteria.where("userName").is("raj")
//        );
        query.addCriteria(Criteria.where("email").is(true));
        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        // As both above conditions are applied individually, they're 'and'


        Criteria criteria = new Criteria();
        query.addCriteria(criteria.orOperator(
                Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"),
                Criteria.where("sentimentAnalysis").is(true)
        ));



//        Now we've to execute this query, whereas in case of MongoRepository just autowired used to work

        List<UserEntity> users = mongoTemplate.find(query, UserEntity.class);
        return users;

    }

}

package net.engineeringdigest.journalApp.scheduler;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepositoryImplementation;
import net.engineeringdigest.journalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {


    @Autowired
    private UserRepositoryImplementation userRepositoryImplementation;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;


    @Scheduled(cron = "0 9 * * SUN") //  Scheduled to run every Sunday at 9 AM
    //    @Scheduled(cron = "*/1 * * * * *") // every sec
    // #Ref: http://www.cronmaker.com/;jsessionid=node01o7yx2uhcg1qn1piffqkpl5a5l777222.node0?0
    //    https://crontab.cronhub.io/
    public void cronExample() {
        List<UserEntity> users = userRepositoryImplementation.getUsersForSentimentAnalysis();
        for (UserEntity user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(entry -> entry.getCreatedDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getContent)
                    .collect(Collectors.toList());


            String entry = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            System.out.println("Cronjob work work work!!!!!!");

        }
    }

}

package com.uzayr.journalApp.scheduler;


import com.uzayr.journalApp.cache.AppCache;
import com.uzayr.journalApp.entity.JournalEntry;
import com.uzayr.journalApp.entity.User;
import com.uzayr.journalApp.enums.Sentiment;
import com.uzayr.journalApp.repository.UserRepositoryImpl;
import com.uzayr.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    // scheduled a cron task to send mail to users who have opted for sentimentAnalysis and have a valid mail,
    // every week 9 AM on a sunday
//    @Scheduled(cron = "0 0 9 * * SUN")

    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();

        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries
                    .stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            // hashmap to get counts of sentiments
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            // find out the most frequent sentiment
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            // if the most frequent sentiment not equal to null, then send a mail
            if (mostFrequentSentiment != null) {
                emailService.sendEmail(
                        user.getEmail(),
                        "Sentiment for last 7 days",
                        mostFrequentSentiment.toString()
                );
            }
        }
    }

    // call the appCache.init() method to clear app cache every 10 minutes
    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}

package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry entry, String userName) {
        try {
            UserEntity user = userService.findByUserName(userName);
            entry.setCreatedDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(entry);
            user.getJournalEntries().add(savedEntry);
//            user.setUserName(null); // Just to test if Transactional would work fine
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error has occurred while saving the entry: ", e);
        }
    }

    public JournalEntry saveEntry(JournalEntry entry) {
        journalEntryRepository.save(entry);
        return entry;
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removedIfFound = false;
        try {
            UserEntity user = userService.findByUserName(userName);
            removedIfFound = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removedIfFound) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error has occurred while saving the entry: ", e);
        }
        return removedIfFound;
    }

}

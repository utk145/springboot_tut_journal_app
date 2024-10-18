package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry entry, String userName) {
        UserEntity user = userService.findByUserName(userName);
        entry.setCreatedDate(LocalDateTime.now());
        JournalEntry savedEntry = journalEntryRepository.save(entry);
        user.getJournalEntries().add(savedEntry);
        userService.saveEntry(user);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName) {
        UserEntity user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId() == id);
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }

}

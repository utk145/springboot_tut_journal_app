package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;


//    #Note: Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests

    @GetMapping
    public ResponseEntity<?> getAllJournals() {
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/create-entry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            journalEntryService.saveEntry(entry);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{entryId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId entryId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(entryId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{entryId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId entryId) {
        journalEntryService.deleteById(entryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-entry/id/{entryId}")
    public ResponseEntity<JournalEntry> updateEntryById(
            @PathVariable ObjectId entryId,
            @RequestBody JournalEntry newEntry
    ) {
        JournalEntry exists = journalEntryService.findById(entryId).orElse(null);
        if (exists != null) {
            exists.setUpdateDate(LocalDateTime.now());
            exists.setTitle(
                    newEntry.getTitle() != null && !newEntry.getTitle().equals("")
                            ?
                            newEntry.getTitle()
                            :
                            exists.getTitle()
            );
            exists.setContent(
                    newEntry.getContent() != null && !newEntry.getContent().isEmpty()
                            ?
                            newEntry.getContent()
                            :
                            exists.getContent()
            );
            journalEntryService.saveEntry(exists);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}

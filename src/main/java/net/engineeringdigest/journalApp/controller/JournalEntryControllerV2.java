package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

//    #Note: Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests

    @GetMapping("/getAllJournalEntriesOfUser")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(allEntries);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Not found any journals related to this particular user");

    }

    @PostMapping("/create-entry")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        journalEntryService.saveEntry(entry, userName);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    @GetMapping("/getJournalById/id/{entryId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId entryId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> listThatShouldContainEntryWithinUserEntriesWithProvidedId = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(entryId))
                .collect(Collectors.toList());
        if (!listThatShouldContainEntryWithinUserEntriesWithProvidedId.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(entryId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteJournalEntryById/id/{entryId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId entryId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean removalStatus = journalEntryService.deleteById(entryId, userName);
        if (removalStatus) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Successfully Deleted");
        }
        return new ResponseEntity<>("Unable to delete the entry", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-entry/id/{entryId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(
            @PathVariable ObjectId entryId,
            @RequestBody JournalEntry newEntry
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> listThatShouldContainEntryWithinUserEntriesWithProvidedId = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(entryId))
                .collect(Collectors.toList());
        if (!listThatShouldContainEntryWithinUserEntriesWithProvidedId.isEmpty()) {
            Optional<JournalEntry> existingEntryOpt = journalEntryService.findById(entryId);

            if (existingEntryOpt.isPresent()) {
                JournalEntry existingEntry = existingEntryOpt.get();
                existingEntry.setUpdateDate(LocalDateTime.now());

                if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                    existingEntry.setTitle(newEntry.getTitle());
                }

                if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                    existingEntry.setContent(newEntry.getContent());
                }

                JournalEntry updatedEntry = journalEntryService.saveEntry(existingEntry);
//                return ResponseEntity.ok(updatedEntry);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(updatedEntry);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        /*
           the use of .build() should be consistent with the context. If you're returning a ResponseEntity with a body, you wouldn't use .build() at the end of ResponseEntity.ok(), since that already creates the response with the provided body.

           Example:
          Use ResponseEntity.ok(body) to create a 200 OK response with a body.
          Use ResponseEntity.status(HttpStatus.NOT_FOUND).build() for a 404 response without a body.
         */
    }
}

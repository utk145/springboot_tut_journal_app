package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {
    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

//    #Note: Methods inside a controller class should be public so that they can be accessed and invoked by the spring framework or external http requests

    @GetMapping
    public List<JournalEntry> getAllJournals() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry entry) {
        System.out.println(journalEntries);
        journalEntries.put(entry.getId(), entry);
        System.out.println(journalEntries);
        return true;
    }

    @GetMapping("id/{entryId}")
    public JournalEntry getJournalEntryById(@PathVariable Long entryId) {
        return journalEntries.get(entryId);
    }

    @DeleteMapping("id/{entryId}")
    public boolean deleteEntryById(@PathVariable Long entryId) {
        journalEntries.remove(entryId);
        return true;
    }

    @PutMapping("/id/{entryId}")
    public JournalEntry updateEntryById(@PathVariable ObjectId entryId, @RequestBody JournalEntry entry) {
        return journalEntries.put(entryId, entry);
    }
}
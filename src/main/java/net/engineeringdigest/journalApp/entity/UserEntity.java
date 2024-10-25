package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import net.engineeringdigest.journalApp.types.UserRoles;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class UserEntity {
    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    /* By default, the indexing doesn't happen, we need to configure it in application.properties. */
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<UserRoles> roles;
}

package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username);
        if (user != null) {
//            Building the User
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles()
                            .stream()
                            // Convert UserRoles to String
                            .map(Enum::name)
                            // Create a String array
                            .toArray(String[]::new))
                    .build();


/*
            Method 2 Avoiding Method References
            String[] rolesArray = user.getRoles()
                    .stream()
                    .map(role -> role.name()) //  Convert UserRoles to String
                    .toArray(size -> new String[size]); // Create a String array with the specified size

            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(rolesArray)
                    .build();
*/

        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}

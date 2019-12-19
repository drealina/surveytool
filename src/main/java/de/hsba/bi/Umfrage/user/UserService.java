package de.hsba.bi.Umfrage.user;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //Konstruktor
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.init();
    }

    //Initiale Useranlage (1x Admin, 2x User)
    @PostConstruct //
    public void init() {
        if (userRepository.count() == 0) {
            createUser("Admin", "admin123", "ADMIN");
            createUser("Test", "test123", "USER");
            createUser("Alina", "alina123", "USER");

        }
    }

    private void createUser(String username, String password, String role) {
        userRepository.save(new User(username, passwordEncoder.encode(password), role));
    }

    public User createUser(User user) {
        String username = user.getName();
        String password = user.getPassword();
        String role = "USER";
        return userRepository.save(new User(username, passwordEncoder.encode(password), role));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUser (Long id){
        return userRepository.getOne(id);
    }

    //Check des Usernames für Registrierung, es dürfen keine Usernames doppelt vorhanden sein
    public boolean existsUsername (String name) {
        if (userRepository.findByName(name)!= null)
        {
            return true;
        }
        return false;
    }

}


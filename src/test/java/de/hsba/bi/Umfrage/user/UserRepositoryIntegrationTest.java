package de.hsba.bi.Umfrage.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() {
        repository.deleteAll(); //löscht alle Einträge der Datenbank
        repository.flush();
    }

    @Test
    public void shouldFindByName() {
        // gegebene User
        User test1 = new User("test1", "", "USER");
        User test2 = new User("test2", "", "USER");
        User test3 = new User("test3", "", "USER");
        repository.save(test1);
        repository.save(test2);
        repository.save(test3);

        // Positiv- und Negativtest
        assertEquals(test1, repository.findByName("test1"));
        assertEquals(test2, repository.findByName("test2"));
        assertEquals(test3, repository.findByName("test3"));
        assertNull(repository.findByName("test4")); //prüft, ob der Negativtest funktoniert
    }
}


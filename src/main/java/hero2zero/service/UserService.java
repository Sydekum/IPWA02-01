package hero2zero.service;

import hero2zero.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.util.List;

/**
 * Service zur Verwaltung und Authentifizierung von Benutzern.
 */
@Named
@ApplicationScoped
public class UserService {

    private EntityManagerFactory emf;

    /**
     * Konstruktor: Initialisiert die EntityManagerFactory.
     */
    public UserService() {
        try {
            this.emf = Persistence.createEntityManagerFactory("hero2zeroPU");
        } catch (Exception e) {
            e.printStackTrace(); // In echten Anwendungen mit Logger arbeiten!
        }
    }

    /**
     * Findet einen Benutzer mit passendem Benutzernamen und Passwort.
     * Wird vom LoginBean verwendet.
     */
    public User findByUsernameAndPassword(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> users = em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username AND u.password = :password",
                            User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();

            return users.isEmpty() ? null : users.get(0);
        } finally {
            em.close();
        }
    }
}

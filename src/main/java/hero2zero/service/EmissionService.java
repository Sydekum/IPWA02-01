package hero2zero.service;

import hero2zero.entity.EmissionData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Service-Klasse für die Verwaltung und Speicherung von Emissionsdaten.
 */
@Named
@ApplicationScoped
public class EmissionService {

    /**
     * EntityManagerFactory zur Erzeugung von EntityManagern für JPA-Zugriffe.
     * Für spätere Entwicklungen oder bei Umfangreicher Nutzung: In größeren Anwendungen besser über @PersistenceUnit
     */
    private EntityManagerFactory emf;

    /**
     * Konstruktor – initialisiert die EntityManagerFactory basierend auf der persistence.xml-Konfiguration.
     */
    public EmissionService() {
        emf = Persistence.createEntityManagerFactory("hero2zeroPU");
    }

    /**
     * Speichert ein übergebenes EmissionData-Objekt in der Datenbank.
     *
     * @param data Das zu speichernde EmissionData-Objekt
     * @return true bei Erfolg, false bei Fehler (inkl. Rollback)
     */
    public boolean saveEmission(EmissionData data) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(data);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
}

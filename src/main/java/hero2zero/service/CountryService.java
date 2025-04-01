package hero2zero.service;

import hero2zero.entity.Country;
import hero2zero.entity.EmissionData;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * Service-Klasse zur Bereitstellung von Länder- und Emissionsdaten.
 * Bietet Zugriff auf alle Länder sowie genehmigte und ungefilterte Emissionsdaten.
 */
@Named
@ApplicationScoped
public class CountryService {

    /**
     * Factory zur Erzeugung von EntityManagern für Datenbankzugriffe via JPA.
     */
    private EntityManagerFactory emf;

    /**
     * Konstruktor – Initialisiert die EntityManagerFactory.
     */
    public CountryService() {
        emf = Persistence.createEntityManagerFactory("hero2zeroPU");
    }

    /**
     * Gibt eine Liste aller Länder zurück, alphabetisch sortiert.
     */
    public List<Country> getAllCountries() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Country c ORDER BY c.name", Country.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Gibt nur die genehmigten Emissionsdaten für ein bestimmtes Land zurück.
     */
    public List<EmissionData> getApprovedEmissionDataByCountryId(Long countryId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT a.emissionData FROM Approval a " +
                                    "WHERE a.emissionData.country.id = :countryId AND a.status = 'approved' " +
                                    "ORDER BY a.emissionData.measureDate DESC",
                            EmissionData.class)
                    .setParameter("countryId", countryId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

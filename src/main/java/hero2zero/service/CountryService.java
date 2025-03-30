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
 * Service-Klasse zur Verwaltung von Ländern und deren Emissionsdaten.
 */
@Named
@ApplicationScoped
public class CountryService {

    /**
     * Factory zur Erzeugung von EntityManagern.
     */
    private EntityManagerFactory emf;

    /**
     * Konstruktor – initialisiert die EntityManagerFactory für den Datenbankzugriff.
     */
    public CountryService() {
        emf = Persistence.createEntityManagerFactory("hero2zeroPU");
    }

    /**
     * Gibt eine alphabetisch sortierte Liste aller Länder zurück.
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
     * Gibt alle Emissionsdaten für ein bestimmtes Land zurück (sortiert nach Datum absteigend).
     */
    public List<EmissionData> getEmissionDataByCountryId(Long countryId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM EmissionData e WHERE e.country.id = :countryId ORDER BY e.measureDate DESC",
                            EmissionData.class)
                    .setParameter("countryId", countryId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

package hero2zero.bean;

import hero2zero.entity.Country;
import hero2zero.entity.EmissionData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;
import java.util.Date;

/**
 * JSF-Backing Bean zur Eingabe und Speicherung von CO₂-Emissionsdaten.
 */
@Named
@RequestScoped
public class EmissionBean implements Serializable {

    /**
     * Vom Benutzer im Frontend ausgewähltes Land.
     */
    private Country selectedCountry;

    /**
     * Eingetragener Emissionswert in kT.
     */
    private double emission;

    /**
     * Datum der Messung.
     */
    private Date measureDate;

    /**
     * Einheit des Emissionswerts (Standard: "kt").
     */
    private String unit = "kt";

    /**
     * Statische Initialisierung der EntityManagerFactory für JPA-Zugriffe.
     */
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hero2zeroPU");

    // ──────────── Getter & Setter ────────────

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public double getEmission() {
        return emission;
    }

    public void setEmission(double emission) {
        this.emission = emission;
    }

    public Date getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(Date measureDate) {
        this.measureDate = measureDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // ──────────── Datenbankoperation ────────────

    /**
     * Speichert die eingegebenen Emissionsdaten in der Datenbank.
     */
    public String saveEmission() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Neues Entity erstellen und befüllen
            EmissionData data = new EmissionData();
            data.setCountry(selectedCountry);
            data.setEmission(emission);
            data.setMeasureDate(measureDate);
            data.setUnit(unit);

            // Persistieren
            em.persist(data);
            em.getTransaction().commit();

            // Erfolgsmeldung für den Benutzer anzeigen
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Gespeichert", "Emissionsdaten wurden erfolgreich gespeichert."));

            return null;
        } catch (Exception e) {
            e.printStackTrace();

            // Im Fehlerfall: Rollback & Fehlermeldung
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Daten konnten nicht gespeichert werden."));

            return null;
        } finally {
            em.close();
        }
    }
}

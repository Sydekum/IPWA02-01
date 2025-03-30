package hero2zero.bean;

import hero2zero.entity.Country;
import hero2zero.entity.EmissionData;
import hero2zero.service.EmissionService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

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
     * Service-Klasse für Datenbankzugriffe.
     */
    @Inject
    private EmissionService emissionService;

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

    // ──────────── View-Logik ────────────

    /**
     * Erstellt ein neues EmissionData-Objekt und speichert es über den EmissionService.
     */
    public String saveEmission() {
        EmissionData data = new EmissionData();
        data.setCountry(selectedCountry);
        data.setEmission(emission);
        data.setMeasureDate(measureDate);
        data.setUnit(unit);

        boolean success = emissionService.saveEmission(data);

        FacesContext context = FacesContext.getCurrentInstance();
        if (success) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Gespeichert", "Emissionsdaten wurden erfolgreich gespeichert."));

            // Eingabefelder zurücksetzen
            selectedCountry = null;
            emission = 0.0;
            measureDate = null;
            unit = "kt";
        } else {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Daten konnten nicht gespeichert werden."));
        }

        return null;
    }
}

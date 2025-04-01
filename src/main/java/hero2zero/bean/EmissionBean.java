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
 * JSF-Backing Bean zur Eingabe und temporären Verwaltung von Emissionsdaten.
 * Bindet die Nutzereingaben aus dem Formular und übergibt sie an den Service zur Speicherung.
 */
@Named
@RequestScoped
public class EmissionBean implements Serializable {

    /**
     * Das im Formular ausgewählte Land.
     */
    private Country selectedCountry;

    /**
     * Vom Nutzer eingegebener Emissionswert in Kilotonnen (kt).
     */
    private double emission;

    /**
     * Datum, an dem die Emission gemessen wurde.
     */
    private Date measureDate;

    /**
     * Einheit der Emissionsmenge (Standard: "kt").
     */
    private String unit = "kt";

    /**
     * Service zur persistenten Speicherung der Emissionsdaten.
     */
    @Inject
    private EmissionService emissionService;

    /**
     * Referenz auf die ApprovalBean, um nach dem Speichern die Ansicht zu aktualisieren.
     */
    @Inject
    private ApprovalBean approvalBean;

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

    // ──────────── Speicherlogik ────────────

    /**
     * Erstellt ein neues EmissionData-Objekt mit den eingegebenen Werten und übergibt es zur Speicherung.
     * Nach erfolgreichem Speichern wird die Approval-Übersicht aktualisiert und Eingabefelder zurückgesetzt.
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
            // Nach Speichern: Approval-Bean aktualisieren (z. B. für Admin-Ansicht)
            approvalBean.loadPendingApprovals();

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

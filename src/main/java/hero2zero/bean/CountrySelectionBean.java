package hero2zero.bean;

import hero2zero.entity.Country;
import hero2zero.entity.EmissionData;
import hero2zero.service.CountryService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * JSF-Backing Bean zur Auswahl eines Landes und Anzeige zugehöriger Emissionsdaten.
 */
@Named
@RequestScoped
public class CountrySelectionBean implements Serializable {

    /**
     * Service für Datenbankzugriffe auf Länder und Emissionsdaten.
     */
    @Inject
    private CountryService countryService;

    /**
     * Die ID des vom Nutzer ausgewählten Landes (aus dem Dropdown).
     */
    private Integer selectedCountryId;

    /**
     * Liste der Emissionsdaten des aktuell ausgewählten Landes.
     */
    private List<EmissionData> emissionDataList;

    /**
     * Liefert alle Länder, z.B. zur Darstellung im Dropdown-Menü.
     */
    public List<Country> getCountries() {
        return countryService.getAllCountries();
    }

    /**
     * Getter für die aktuell ausgewählte Länder-ID.
     */
    public Integer getSelectedCountryId() {
        return selectedCountryId;
    }

    /**
     * Setter für die aktuell ausgewählte Länder-ID
     */
    public void setSelectedCountryId(Integer selectedCountryId) {
        this.selectedCountryId = selectedCountryId;
    }

    /**
     * Gibt die Liste der geladenen Emissionsdaten zurück.
     */
    public List<EmissionData> getEmissionDataList() {
        return emissionDataList;
    }

    /**
     * Wird im UI verwendet, um festzustellen, ob keine Daten angezeigt werden können.
     */
    public boolean isNoDataAvailable() {
        return emissionDataList == null || emissionDataList.isEmpty();
    }

    /**
     * Lädt die Emissionsdaten für das aktuell ausgewählte Land.
     * Wird durch Button im Frontend ausgelöst.
     */
    public String fetch() {
        if (selectedCountryId != null) {
            emissionDataList = countryService.getApprovedEmissionDataByCountryId(Long.valueOf(selectedCountryId));
        }
        return null; // Bleibt auf derselben Seite
    }
}

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
 * JSF-Backing Bean zur Auswahl eines Landes und Anzeige der zugehörigen CO₂-Emissionsdaten.
 */
@Named
@RequestScoped
public class CountrySelectionBean implements Serializable {

    /**
     * Zugriff auf den CountryService, der die Datenbankabfragen durchführt.
     */
    @Inject
    private CountryService countryService;

    /**
     * Vom Benutzer im Frontend ausgewählte Länder-ID (z.B. aus Dropdown).
     */
    private Integer selectedCountryId;

    /**
     * Ergebnisliste der Emissionsdaten für das ausgewählte Land.
     */
    private List<EmissionData> emissionDataList;

    /**
     * Gibt alle Länder zurück (für Dropdown-Auswahl im Frontend).
     */
    public List<Country> getCountries() {
        return countryService.getAllCountries();
    }

    /**
     * Gibt die vom Benutzer ausgewählte Länder-ID zurück.
     */
    public Integer getSelectedCountryId() {
        return selectedCountryId;
    }

    /**
     * Setzt die vom Benutzer ausgewählte Länder-ID.
     */
    public void setSelectedCountryId(Integer selectedCountryId) {
        this.selectedCountryId = selectedCountryId;
    }

    /**
     * Gibt die Liste der Emissionsdaten für das gewählte Land zurück.
     */
    public List<EmissionData> getEmissionDataList() {
        return emissionDataList;
    }

    /**
     * Prüft, ob keine Emissionsdaten vorhanden sind.
     */
    public boolean isNoDataAvailable() {
        return emissionDataList == null || emissionDataList.isEmpty();
    }

    /**
     * Methode zur Datenabfrage – wird im Frontend z.B. bei Button-Klick aufgerufen.
     * Wenn ein Land gewählt ist, werden dessen Emissionsdaten bei Bestätigung geladen.
     */
    public String fetch() {
        if (selectedCountryId != null) {
            emissionDataList = countryService.getEmissionDataByCountryId(Long.valueOf(selectedCountryId));
        }
        return null; // bleibt auf der aktuellen Seite
    }
}

package hero2zero.converter;

import hero2zero.entity.Country;
import hero2zero.service.CountryService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

/**
 * JSF-Converter für Country-Objekte.
 */
@FacesConverter(value = "countryConverter", managed = true) // managed=true erlaubt @Inject
public class CountryConverter implements Converter<Country> {

    /**
     * Wandelt den String-Wert (z.B. "3") in ein Country-Objekt um.
     */
    @Override
    public Country getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try {
            int id = Integer.parseInt(value);

            // Den CountryService aus dem CDI-Kontext holen
            CountryService service = context.getApplication()
                    .evaluateExpressionGet(context, "#{countryService}", CountryService.class);

            // Alle Länder durchgehen und nach ID matchen
            return service.getAllCountries()
                    .stream()
                    .filter(c -> c.getId() == id)
                    .findFirst()
                    .orElse(null);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Wandelt ein Country-Objekt in seinen String-Wert (ID) um.
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Country country) {
        if (country == null) return "";
        return String.valueOf(country.getId());
    }
}

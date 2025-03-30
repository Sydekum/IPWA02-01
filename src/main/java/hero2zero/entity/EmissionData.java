package hero2zero.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entity-Klasse zur Abbildung eines CO₂-Messwerts eines Landes zu einem bestimmten Zeitpunkt.
 * Verknüpft mit der Tabelle 'EmissionData' in der DB.
 */
@Entity
@Table(name = "EmissionData")
public class EmissionData {

    /**
     * Primärschlüssel – eindeutig identifizierender Schlüssel für jeden Emissionsdatensatz.
     * Wird automatisch von der DB generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Gemessene CO₂-Emission.
     */
    @Column(nullable = false)
    private double emission;

    /**
     * Datum, an dem die Emission gemessen wurde.
     * Wird als  Datum (ohne Zeit) gespeichert.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "measure_date", nullable = false)
    private Date measureDate;

    /**
     * Einheit der Messung (kt).
     * Pflichtfeld mit maximal 50 Zeichen.
     */
    @Column(length = 50, nullable = false)
    private String unit;

    /**
     * Zugehöriges Land, für das der Emissionswert gemessen wurde.
     */
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // ──────────── Getter & Setter ────────────

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}

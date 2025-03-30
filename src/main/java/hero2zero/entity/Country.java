package hero2zero.entity;

import jakarta.persistence.*;

/**
 * Entity-Klasse zur Abbildung eines Landes (Country) in der Datenbank.
 * Wird über JPA mit der Tabelle 'Country' verbunden.
 */
@Entity
@Table(name = "Country")
public class Country {

    /**
     * Primärschlüssel (eindeutige ID eines Landes).
     * Die ID wird automatisch von der Datenbank generiert (Auto-Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Name des Landes (Pflichtfeld, max. 100 Zeichen).
     */
    @Column(length = 100, nullable = false)
    private String name;

    // ──────────── Getter & Setter ────────────

    /**
     * Gibt die ID des Landes zurück.
     */
    public int getId() { return id; }

    /**
     * Setzt die ID des Landes.
     * Wird in der Regel nicht verwendet, da die ID automatisch durch die DB vergeben wird.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Gibt den Namen des Landes zurück.
     */
    public String getName() { return name; }

    /**
     * Setzt den Namen des Landes.
     */
    public void setName(String name) { this.name = name; }

    // ──────────── equals & hashCode ────────────

    /**
     * Überschreibt equals(), um zwei Country-Objekte anhand ihrer ID zu vergleichen.
     * Notwendig für korrekte Darstellung in JSF-Komponenten
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Selbstvergleich
        if (obj == null || getClass() != obj.getClass()) return false; // anderer Typ
        Country other = (Country) obj;
        return id == other.id; // Vergleich der IDs
    }

    /**
     * Hashcode-Berechnung auf Basis der ID.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

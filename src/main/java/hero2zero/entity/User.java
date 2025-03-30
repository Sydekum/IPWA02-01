package hero2zero.entity;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity-Klasse zur Abbildung eines Benutzersystems.
 * Repräsentiert die Tabelle 'User' in der Datenbank.
 */
@Entity
@Table(name = "User")
public class User implements Serializable {

    /**
     * Primärschlüssel des Benutzers.
     * Wird automatisch generiert (Auto-Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Eindeutiger Benutzername (Pflichtfeld, max. 50 Zeichen).
     * Wird zur Anmeldung verwendet.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Passwort (Pflichtfeld, max. 255 Zeichen).
     */
    @Column(nullable = false, length = 255)
    private String password;

    /**
     * Rolle des Benutzers (z.B. Wissenschafftler)
     * Aktuell Optional, max. 50 Zeichen.
     */
    @Column(length = 50)
    private String role;

    // ──────────── Getter & Setter ────────────

    /**
     * Gibt die ID des Benutzers zurück.
     */
    public int getId() {
        return id;
    }

    /**
     * Setzt die ID des Benutzers.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gibt den Benutzernamen zurück.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setzt den Benutzernamen.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gibt das Passwort zurück
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setzt das Passwort.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gibt die Benutzerrolle zurück (z.B. "Admin", "Wissenschaftler"), wird später für den approval Workflow benötigt
     */
    public String getRole() {
        return role;
    }

    /**
     * Setzt die Rolle des Benutzers.
     */
    public void setRole(String role) {
        this.role = role;
    }
}

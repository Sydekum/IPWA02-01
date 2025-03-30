package hero2zero.bean;

import hero2zero.entity.User;
import hero2zero.service.UserService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;

/**
 * Bean zur Verwaltung des Logins und der Session eines Benutzers.
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Benutzername, der im Login-Formular eingegeben wurde.
     */
    private String username;

    /**
     * Passwort, das im Login-Formular eingegeben wurde.
     * Hinweis: Für zukünftige Erweiterungen Verschlüsselt umsetzen
     */
    private String password;

    /**
     * Der aktuell eingeloggte Benutzer (null, wenn niemand eingeloggt ist).
     */
    private User loggedInUser;

    /**
     * Service-Klasse zur Benutzerauthentifizierung.
     */
    @Inject
    private UserService userService;

    // ──────────── Login-Funktion ────────────

    /**
     * Versucht, den Benutzer mit den eingegebenen Zugangsdaten anzumelden.
     * Bei Erfolg: Speichert den Benutzer in der Session und leitet weiter.
     * Bei Misserfolg: Zeigt eine Fehlermeldung an.
     */
    public String login() {
        try {
            User user = userService.findByUsernameAndPassword(username, password);

            if (user != null) {
                loggedInUser = user;
                return "addEmission.xhtml?faces-redirect=true"; // Weiterleitung nach Login
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login fehlgeschlagen", "Benutzername oder Passwort ist ungültig."));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", "Beim Login ist ein Fehler aufgetreten."));
            return null;
        }
    }

    // ──────────── Logout-Funktion ────────────

    /**
     * Setzt alle Session-Werte zurück und leitet zur Startseite weiter.
     */
    public String logout() {
        loggedInUser = null;
        username = null;
        password = null;
        return "index.xhtml?faces-redirect=true";
    }

    // ──────────── Zugriffskontrolle ────────────

    /**
     * Prüft, ob ein Benutzer eingeloggt ist. Falls nicht, erfolgt eine Weiterleitung zur Login-Seite.
     */
    public String checkAccess() {
        if (loggedInUser == null) {
            return "index.xhtml?faces-redirect=true";
        }
        return null;
    }

    // ──────────── Getter & Setter ────────────

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Gibt zurück, ob aktuell ein Benutzer eingeloggt ist.
     */
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}

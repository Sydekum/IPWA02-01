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
 * Session-Scoped Bean zur Benutzerverwaltung (Login, Logout, Zugriffskontrolle).
 * Beinhaltet Authentifizierung, Session-Zustand und Rollenprüfung.
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Eingegebener Benutzername im Login-Formular.
     */
    private String username;

    /**
     * Eingegebenes Passwort im Login-Formular.
     * Hinweis: Noch unverschlüsselt gespeichert
     */
    private String password;

    /**
     * Der aktuell eingeloggte Benutzer.
     * Null, wenn keine Authentifizierung erfolgt ist.
     */
    private User loggedInUser;

    /**
     * Service zur Benutzerauthentifizierung über die DB.
     */
    @Inject
    private UserService userService;

    // ──────────── Login ────────────

    /**
     * Führt die Anmeldung anhand der eingegebenen Zugangsdaten durch.
     */
    public String login() {
        try {
            User user = userService.findByUsernameAndPassword(username, password);

            if (user != null) {
                loggedInUser = user;
                return "addEmission.xhtml?faces-redirect=true";
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

    // ──────────── Logout ────────────

    /**
     * Beendet die Session des Benutzers und leitet zurück zur Startseite.
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    // ──────────── Zugriffskontrolle ────────────

    /**
     * Überprüft, ob der Benutzer angemeldet ist.
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
     * Gibt zurück, ob ein Benutzer aktuell eingeloggt ist.
     */
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    // ──────────── Rollenprüfung ────────────

    /**
     * Prüft, ob der eingeloggte Benutzer die Rolle "Admin" oder "Herausgeber" hat.
     */
    public boolean isEditorOrAdmin() {
        return loggedInUser != null &&
                ("Admin".equals(loggedInUser.getRole()) || "Herausgeber".equals(loggedInUser.getRole()));
    }
}

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

    private String username;
    private String password;
    private User loggedInUser;

    @Inject
    private UserService userService;

    // ──────────── Login ────────────

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

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    // ──────────── Zugriffskontrolle ────────────

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

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
}

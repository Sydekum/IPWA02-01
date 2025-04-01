package hero2zero.service;

import hero2zero.bean.LoginBean;
import hero2zero.entity.Approval;
import hero2zero.entity.EmissionData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Service-Klasse zur Speicherung neuer Emissionsdaten und zugehöriger Approval-Vorgänge.
 * Speichert neue Einträge in der Datenbank und erstellt automatisch einen Genehmigungsantrag.
 */
@Named
@ApplicationScoped
public class EmissionService {

    /**
     * EntityManagerFactory zur Erstellung von EntityManagern für JPA-Zugriffe.
     */
    private EntityManagerFactory emf;

    /**
     * Referenz auf die LoginBean, um den aktuell eingeloggten Benutzer zu ermitteln.
     * Wird verwendet, um den "proposedBy"-Wert für neue Approval-Einträge zu setzen.
     */
    @Inject
    private LoginBean loginBean;

    /**
     * Konstruktor: Initialisiert die EntityManagerFactory basierend auf persistence.xml.
     */
    public EmissionService() {
        emf = Persistence.createEntityManagerFactory("hero2zeroPU");
    }

    /**
     * Speichert einen neuen Emissionsdatensatz in der Datenbank und legt automatisch
     * einen zugehörigen Approval-Eintrag mit dem Status "pending" an.
     */
    public boolean saveEmission(EmissionData data) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Emissionsdaten speichern
            em.persist(data);
            em.flush(); // Sicherstellen, dass ID generiert ist

            // Genehmigungsantrag vorbereiten
            Approval approval = new Approval();
            approval.setEmissionData(data);
            approval.setStatus(Approval.Status.pending);
            approval.setProposedBy(loginBean.getLoggedInUser());

            // Approval speichern
            em.persist(approval);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
}

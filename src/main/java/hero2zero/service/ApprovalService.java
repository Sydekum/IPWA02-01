package hero2zero.service;

import hero2zero.entity.Approval;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Genehmigungsanfragen (Approval).
 */
@Named
@ApplicationScoped
public class ApprovalService {

    /**
     * EntityManagerFactory zur Erstellung von EntityManagern für JPA-Zugriffe.
     */
    private EntityManagerFactory emf;

    /**
     * Konstruktor: Initialisiert die EntityManagerFactory aus der persistence.xml.
     */
    public ApprovalService() {
        emf = Persistence.createEntityManagerFactory("hero2zeroPU");
    }

    /**
     * Liefert alle Einträge, die sich aktuell im Status "pending" befinden.
     */
    public List<Approval> getPendingApprovals() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Approval> query = em.createQuery(
                    "SELECT a FROM Approval a WHERE a.status = :status", Approval.class);
            query.setParameter("status", Approval.Status.pending);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Aktualisiert den Status eines bestimmten Eintrags.
     */
    public void updateApprovalStatus(Approval approval, Approval.Status newStatus) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();


            Approval managedApproval = em.find(Approval.class, approval.getId());
            if (managedApproval != null) {
                managedApproval.setStatus(newStatus);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}

package hero2zero.bean;

import hero2zero.entity.Approval;
import hero2zero.service.ApprovalService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

/**
 * JSF-Session-Scoped Bean zur Verwaltung von Emissionsdaten-Freigaben.
 * Wird genutzt, um offene Genehmigungen anzuzeigen und Aktionen wie Genehmigung/Ablehnung auszuführen.
 */
@Named
@SessionScoped
public class ApprovalBean implements Serializable {

    /**
     * Service zur Kommunikation mit der Datenbank bezüglich Approval-Einträgen.
     */
    @Inject
    private ApprovalService approvalService;

    /**
     * Liste aller aktuell ausstehenden (noch nicht freigegebenen) Genehmigungsanfragen.
     */
    private List<Approval> pendingApprovals;

    /**
     * Wird automatisch nach Erzeugung der Bean aufgerufen.
     * Lädt die offenen Genehmigungsanfragen aus der Datenbank.
     */
    @PostConstruct
    public void init() {
        loadPendingApprovals();
    }

    /**
     * Ruft die aktuell offenen Genehmigungen aus der Datenbank ab
     * und speichert sie in der Bean.
     */
    public void loadPendingApprovals() {
        pendingApprovals = approvalService.getPendingApprovals();
    }

    /**
     * Genehmigt eine Emission durch Aktualisierung des Status.
     */
    public void approve(Approval approval) {
        approvalService.updateApprovalStatus(approval, Approval.Status.approved);
        loadPendingApprovals(); // Liste aktualisieren
    }

    /**
     * Lehnen eine Emission ab durch Aktualisierung des Status.
     */
    public void reject(Approval approval) {
        approvalService.updateApprovalStatus(approval, Approval.Status.rejected);
        loadPendingApprovals(); // Liste aktualisieren
    }

    /**
     * Getter für die Liste ausstehender Genehmigungen.
     */
    public List<Approval> getPendingApprovals() {
        return pendingApprovals;
    }
}

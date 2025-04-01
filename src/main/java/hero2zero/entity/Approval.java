package hero2zero.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Entity zur Abbildung eines Genehmigungsstatus für Emissionsdaten.
 * Enthält Informationen zur Freigabe, dem Bearbeitungszeitpunkt sowie dem zugehörigen Datensatz.
 */
@Entity
public class Approval implements Serializable {

    /**
     * Primärschlüssel, wird durch DB generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Aktueller Status der Genehmigung (pending, approved, rejected).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    /**
     * Zeitpunkt der letzten Statusänderung (automatisch gesetzt).
     */
    @Column(nullable = false)
    private Timestamp changeDate;

    /**
     * Referenz auf den zu genehmigenden Emissionsdatensatz (1:1).
     */
    @OneToOne
    @JoinColumn(name = "emissiondata_id", nullable = false)
    private EmissionData emissionData;

    /**
     * Benutzer, der den Emissionsdatensatz eingetragen hat.
     */
    @ManyToOne
    @JoinColumn(name = "proposed_by", nullable = false)
    private User proposedBy;

    /**
     * Mögliche Statuswerte für ein Approval.
     */
    public enum Status {
        pending, approved, rejected
    }

    /**
     * Wird automatisch beim ersten Speichern aufgerufen.
     * Setzt das Änderungsdatum und den Standardstatus auf "pending".
     */
    @PrePersist
    protected void onCreate() {
        changeDate = new Timestamp(System.currentTimeMillis());
        if (status == null) {
            status = Status.pending;
        }
    }

    // ──────────── Getter & Setter ────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Setzt den Genehmigungsstatus und aktualisiert automatisch das Änderungsdatum.
     */
    public void setStatus(Status status) {
        this.status = status;
        this.changeDate = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getChangeDate() {
        return changeDate;
    }

    public EmissionData getEmissionData() {
        return emissionData;
    }

    public void setEmissionData(EmissionData emissionData) {
        this.emissionData = emissionData;
    }

    public User getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(User proposedBy) {
        this.proposedBy = proposedBy;
    }
}

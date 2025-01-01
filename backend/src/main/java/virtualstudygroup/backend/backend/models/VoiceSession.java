package virtualstudygroup.backend.backend.models;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "voice_session")
public class VoiceSession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voice_sessions_id_gen")
    @SequenceGenerator(name = "voice_sessions_id_gen", sequenceName = "voice_sessions_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_room_id", nullable = false)
    private VirtualRoom virtualRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "status", nullable = false) // ACTIVE, ENDED
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(name = "ended_at")
    private Instant endedAt;
}
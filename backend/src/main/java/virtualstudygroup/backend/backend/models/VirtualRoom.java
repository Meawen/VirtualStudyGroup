package virtualstudygroup.backend.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "virtual_rooms")
public class VirtualRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "virtual_rooms_id_gen")
    @SequenceGenerator(name = "virtual_rooms_id_gen", sequenceName = "virtual_rooms_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudyGroup group;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "settings", columnDefinition = "jsonb")
    private String settings; // Store chat/voice settings in JSON format

    @Column(name = "created_at", nullable = false, updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(name = "timer", nullable = false)
    @ColumnDefault("0")
    private Integer timer;

    @OneToMany(mappedBy = "virtualRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @OneToMany(mappedBy = "virtualRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoiceSession> voiceSessions;
}

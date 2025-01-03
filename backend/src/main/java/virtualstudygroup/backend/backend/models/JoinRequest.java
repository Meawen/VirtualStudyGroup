package virtualstudygroup.backend.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.User;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "join_requests")
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "join_requests_id_gen")
    @SequenceGenerator(name = "join_requests_id_gen", sequenceName = "join_requests_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudyGroup group;

    @Column(name = "status", nullable = false)
    @ColumnDefault("'PENDING'") // Status može biti PENDING, ACCEPTED, REJECTED
    private String status;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant createdAt;

}



package virtualstudygroup.backend.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "study_groups")
public abstract class StudyGroup {

    public abstract boolean userCanJoin(Integer userId);


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "study_groups_id_gen")
    @SequenceGenerator(name = "study_groups_id_gen", sequenceName = "study_groups_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VirtualRoom> virtualRooms;

    @Size(max = 50)
    @NotNull
    @ColumnDefault("'PUBLIC'") // Default to 'PUBLIC'
    @Column(name = "visibility", nullable = false)
    private String visibility; // PUBLIC or PRIVATE
}
package org.moodle.springlaboratorywork.entity;


import jakarta.persistence.*;
import lombok.*;
import org.moodle.springlaboratorywork.entity.embedded.PlayerStatistic;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "goals", column = @Column(name = "goals")),
            @AttributeOverride(name = "assists", column = @Column(name = "assists"))
    })
    private PlayerStatistic playerStatistic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }


}

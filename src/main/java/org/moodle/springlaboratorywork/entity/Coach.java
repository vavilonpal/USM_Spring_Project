package org.moodle.springlaboratorywork.entity;


import ch.qos.logback.core.testUtil.TeeOutputStream;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "coaches")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;

    @OneToOne
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

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

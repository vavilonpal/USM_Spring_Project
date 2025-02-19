package org.moodle.springlaboratorywork.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "league_id")
    @Builder.Default
    private Set<Team> teams = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "league_id")
    @Builder.Default
    private List<Match> matches = new ArrayList<>();

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

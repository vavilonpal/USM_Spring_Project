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
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @OneToMany
    @JoinColumn(name = "team_id")
    @Builder.Default
    private List<Player> players = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "home_team_id")
    @Builder.Default
    private Set<Match> homeMatches = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "away_team_id")
    @Builder.Default
    private Set<Match> awayMatches = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

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

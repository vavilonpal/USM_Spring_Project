package org.moodle.springlaboratorywork.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /*@OneToMany(orphanRemoval = true)
    @JoinColumn(name = "team_id")
    @Builder.Default
    private List<Match> matches = new ArrayList<>();
*/
    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    public void setPlayer(Player player){
        this.players.add(player);
    }
    @PrePersist
    void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }


}

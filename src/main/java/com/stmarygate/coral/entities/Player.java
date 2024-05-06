package com.stmarygate.coral.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "players")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(name = "experience_points", nullable = false)
  private Long exp;

  @Column(name = "exp_points_to_next_lvl", nullable = false, columnDefinition = "BIGINT DEFAULT " +
          "200")
  private Long expToNextLevel;

  @Column(name = "player_level", nullable = false)
  private int level;

  @Column(name = "mana_gauge", nullable = false)
  private Long mana;

  @Column(name = "aura_value", nullable = false)
  private int aura;

  @Column(name = "strength_value", nullable = false)
  private int strength;

  @Column(name = "defense_value", nullable = false)
  private int defense;

  @Column(name = "speed_value", nullable = false)
  private int speed;

  @Column(name = "health_points", nullable = false)
  private int health;

  @Column(name = "max_health_points", nullable = false, columnDefinition = "INT DEFAULT 100")
  private int maxHealth;

  @Column(name = "stamina_points", nullable = false)
  private Long stamina;
}

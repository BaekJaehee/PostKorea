package com.ssafy.dmobile.theme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "Theme")
@NoArgsConstructor
public class Theme {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Long themeId;

    private String theme_name;
    private String description;

//    @OneToMany(mappedBy = "themerelicrelation")
//    private Set<ThemeRelicRelation> themeRelicRelations = new HashSet<>();
}

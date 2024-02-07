package com.ssafy.dmobile.theme.entity;

import com.ssafy.dmobile.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "theme")
public class Theme {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Long themeId;

    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    private Set<ThemeRelic> themeRelics = new HashSet<>();

//    @Builder
//    public Theme(Long themeId, String themeName, String description, Set<ThemeRelic> themeRelics) {
//        this.themeId = themeId;
//        this.themeName = themeName;
//        this.description = description;
//        this.themeRelics = themeRelics;
//    }

    public void update(String themeName, String description) {
        this.themeName = themeName;
        this.description = description;
    }

}

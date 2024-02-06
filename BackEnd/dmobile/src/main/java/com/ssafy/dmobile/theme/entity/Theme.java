//package com.ssafy.dmobile.theme.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Getter
//@Table(name = "theme")
//@NoArgsConstructor
//public class Theme {
//
//    @EmbeddedId
//    private ThemeKey key;
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "theme_id")
//    private Long themeId;
//
//    @Column(name = "theme_name")
//    private String themeName;
//
//    @Column(name = "description")
//    private String description;
//
////    @OneToMany(mappedBy = "themerelicrelation")
////    private Set<ThemeRelicRelation> themeRelicRelations = new HashSet<>();
//}

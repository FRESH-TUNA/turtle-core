//package com.remember.core.domains;
//
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@NoArgsConstructor
//public class Algorithm extends BaseTimeDomain {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 100, nullable = false)
//    private String title;
//
//    @ManyToMany
//    @JoinTable(name = "composer_music",
//            joinColumns = @JoinColumn(name = "composer_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
//    private List<Composer> composers = new ArrayList<>();
//}

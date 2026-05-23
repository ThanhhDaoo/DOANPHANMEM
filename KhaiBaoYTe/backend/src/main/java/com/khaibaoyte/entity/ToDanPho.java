package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TODANPHO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDanPho {

    @Id
    @Column(name = "maToDanPho", columnDefinition = "char(4)", length = 4)
    private String maToDanPho;

    @Column(name = "tenToDanPho", nullable = false, length = 100)
    private String tenToDanPho;

    @Column(name = "moTa", length = 255)
    private String moTa;
}

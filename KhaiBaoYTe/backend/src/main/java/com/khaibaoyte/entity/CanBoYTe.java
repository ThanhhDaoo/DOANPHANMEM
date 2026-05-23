package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CANBOYTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanBoYTe {

    @Id
    @Column(name = "maCanBo", columnDefinition = "char(6)", length = 6)
    private String maCanBo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan", referencedColumnName = "maNguoiDan", nullable = false, columnDefinition = "char(6)", unique = true)
    private NguoiDan nguoiDan;

    public String getMaNguoiDan() {
        return nguoiDan != null ? nguoiDan.getMaNguoiDan() : null;
    }

    @Column(name = "chucVu", nullable = false, length = 100)
    private String chucVu;
}

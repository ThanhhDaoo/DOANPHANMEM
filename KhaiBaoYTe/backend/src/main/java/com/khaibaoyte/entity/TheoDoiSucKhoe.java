package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "THEODOISUCKHOE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheoDoiSucKhoe {

    @Id
    @Column(name = "maTheoDoi", columnDefinition = "char(7)", length = 7)
    private String maTheoDoi;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan", referencedColumnName = "maNguoiDan", nullable = false, columnDefinition = "char(6)")
    private NguoiDan nguoiDan;

    public String getMaNguoiDan() {
        return nguoiDan != null ? nguoiDan.getMaNguoiDan() : null;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maCanBo", referencedColumnName = "maCanBo", nullable = false, columnDefinition = "char(6)")
    private CanBoYTe canBoYTe;

    public String getMaCanBo() {
        return canBoYTe != null ? canBoYTe.getMaCanBo() : null;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trangThaiSucKhoe", referencedColumnName = "maTrangThai", nullable = false, columnDefinition = "char(5)")
    private TrangThaiSucKhoe trangThaiSucKhoe;

    public String getMaTrangThai() {
        return trangThaiSucKhoe != null ? trangThaiSucKhoe.getMaTrangThai() : null;
    }

    @Column(name = "mucDoNguyCo", nullable = false, length = 30)
    private String mucDoNguyCo;

    @Lob
    @Column(name = "ghiChuChiTiet", nullable = false, columnDefinition = "nvarchar(max)")
    private String ghiChuChiTiet;

    @Column(name = "thoiGianCapNhat", nullable = false, insertable = false, updatable = false)
    private LocalDateTime thoiGianCapNhat;
}

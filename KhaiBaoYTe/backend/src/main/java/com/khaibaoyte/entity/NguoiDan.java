package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "NGUOIDAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NguoiDan {

    @Id
    @Column(name = "maNguoiDan", columnDefinition = "char(6)", length = 6)
    private String maNguoiDan;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maToDanPho", columnDefinition = "char(4)")
    private ToDanPho toDanPho;

    public String getMaToDanPho() {
        return toDanPho != null ? toDanPho.getMaToDanPho() : null;
    }

    @Column(name = "CCCD", nullable = false, columnDefinition = "char(12)", length = 12, unique = true)
    private String cccd;

    @Column(name = "hoTen", nullable = false, length = 70)
    private String hoTen;

    @Column(name = "ngaySinh", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "gioiTinh", nullable = false)
    private Boolean gioiTinh;

    @Column(name = "soDienThoai", nullable = false, length = 10, unique = true)
    private String soDienThoai;

    @Column(name = "eMail", length = 50)
    private String email;

    @Column(name = "diaChiNha", nullable = false, length = 100)
    private String diaChiNha;

    @Column(name = "matKhau", nullable = false, length = 60)
    private String matKhau;

    @Column(name = "ngayTao", nullable = false, insertable = false, updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "trangThai", nullable = false, length = 50)
    private String trangThai;

    @Column(name = "anhDaiDien", length = 255)
    private String anhDaiDien;

    @Column(name = "vaiTro", nullable = false)
    private Boolean vaiTro;
}

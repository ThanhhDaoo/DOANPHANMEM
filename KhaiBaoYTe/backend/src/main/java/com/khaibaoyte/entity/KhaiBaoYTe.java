package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "KHAIBAOYTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhaiBaoYTe {

    @Id
    @Column(name = "maKhaiBao", columnDefinition = "char(7)", length = 7)
    private String maKhaiBao;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan", referencedColumnName = "maNguoiDan", nullable = false, columnDefinition = "char(6)")
    private NguoiDan nguoiDan;

    public String getMaNguoiDan() {
        return nguoiDan != null ? nguoiDan.getMaNguoiDan() : null;
    }

    @Column(name = "thoiGianKhaiBao", nullable = false, insertable = false, updatable = false)
    private LocalDateTime thoiGianKhaiBao;

    @Column(name = "nhietDo", nullable = false)
    private Double nhietDo;

    @Lob
    @Column(name = "trieuChung", columnDefinition = "nvarchar(max)")
    private String trieuChung;

    @Lob
    @Column(name = "lichSuDiChuyen", columnDefinition = "nvarchar(max)")
    private String lichSuDiChuyen;

    @Lob
    @Column(name = "lichSuTiepXuc", columnDefinition = "nvarchar(max)")
    private String lichSuTiepXuc;

    @Lob
    @Column(name = "ghiChu", columnDefinition = "nvarchar(max)")
    private String ghiChu;

    @Column(name = "trangThaiXuLy", nullable = false, length = 50)
    private String trangThaiXuLy;
}

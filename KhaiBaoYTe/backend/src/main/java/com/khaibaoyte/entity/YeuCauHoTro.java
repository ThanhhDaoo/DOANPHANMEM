package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "YEUCAUHOTRO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YeuCauHoTro {

    @Id
    @Column(name = "maYeuCau", columnDefinition = "char(7)", length = 7)
    private String maYeuCau;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan", referencedColumnName = "maNguoiDan", nullable = false, columnDefinition = "char(6)")
    private NguoiDan nguoiDan;

    @Column(name = "loaiYeuCau", nullable = false, length = 30)
    private String loaiYeuCau;

    @Column(name = "tieuDe", nullable = false, length = 50)
    private String tieuDe;

    @Column(name = "noiDung", nullable = false, length = 255)
    private String noiDung;

    @Column(name = "thoiGianGui", nullable = false, insertable = false, updatable = false)
    private LocalDateTime thoiGianGui;

    @Column(name = "trangThai", nullable = false, length = 20)
    private String trangThai;
}

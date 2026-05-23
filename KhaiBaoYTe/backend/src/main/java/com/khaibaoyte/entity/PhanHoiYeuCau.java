package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PHANHOIYEUCAU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanHoiYeuCau {

    @Id
    @Column(name = "maPhanHoi", columnDefinition = "char(7)", length = 7)
    private String maPhanHoi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maYeuCau", referencedColumnName = "maYeuCau", nullable = false, columnDefinition = "char(7)")
    private YeuCauHoTro yeuCauHoTro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maCanBo", referencedColumnName = "maCanBo", nullable = false, columnDefinition = "char(6)")
    private CanBoYTe canBoYTe;

    @Column(name = "noiDung", nullable = false, length = 255)
    private String noiDung;

    @Column(name = "thoiGianPhanHoi", nullable = false, insertable = false, updatable = false)
    private LocalDateTime thoiGianPhanHoi;
}

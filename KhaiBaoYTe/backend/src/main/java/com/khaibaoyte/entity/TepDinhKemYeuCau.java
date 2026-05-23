package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TEPDINHKEM_YEUCAU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TepDinhKemYeuCau {

    @Id
    @Column(name = "maTep", columnDefinition = "char(7)", length = 7)
    private String maTep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maYeuCau", referencedColumnName = "maYeuCau", nullable = false, columnDefinition = "char(7)")
    private YeuCauHoTro yeuCauHoTro;

    @Column(name = "tenTep", nullable = false, length = 255)
    private String tenTep;

    @Column(name = "loaiTep", nullable = false, length = 50)
    private String loaiTep;

    @Column(name = "duongDan", nullable = false, length = 255)
    private String duongDan;
}

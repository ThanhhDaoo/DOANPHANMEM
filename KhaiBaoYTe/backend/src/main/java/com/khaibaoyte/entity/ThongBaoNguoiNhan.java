package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "THONGBAO_NGUOINHAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongBaoNguoiNhan {

    @EmbeddedId
    private ThongBaoNguoiNhanId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maThongBao")
    @JoinColumn(name = "maThongBao", columnDefinition = "char(7)", nullable = false)
    private ThongBao thongBao;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maNguoiDan")
    @JoinColumn(name = "maNguoiDan", columnDefinition = "char(6)", nullable = false)
    private NguoiDan nguoiDan;

    @Column(name = "trangThaiDoc", nullable = false)
    private Boolean trangThaiDoc;

    @Column(name = "thoiGianDoc")
    private LocalDateTime thoiGianDoc;
}

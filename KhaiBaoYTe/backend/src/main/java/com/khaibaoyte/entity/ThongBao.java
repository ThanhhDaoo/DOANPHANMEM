package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "THONGBAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongBao {

    @Id
    @Column(name = "maThongBao", columnDefinition = "char(7)", length = 7)
    private String maThongBao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maCanBo", referencedColumnName = "maCanBo", nullable = false, columnDefinition = "char(6)")
    private CanBoYTe canBoYTe;

    @Column(name = "tieuDe", nullable = false, length = 50)
    private String tieuDe;

    @Column(name = "noiDung", nullable = false, length = 255)
    private String noiDung;

    @Column(name = "loaiThongBao", nullable = false, length = 30)
    private String loaiThongBao;

    @Column(name = "thoiGianGui", nullable = false, insertable = false, updatable = false)
    private LocalDateTime thoiGianGui;
}

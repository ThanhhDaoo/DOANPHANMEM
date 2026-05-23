package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAIDANGTINTUC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaiDangTinTuc {

    @Id
    @Column(name = "maBaiDang", columnDefinition = "char(7)", length = 7)
    private String maBaiDang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maCanBo", referencedColumnName = "maCanBo", nullable = false, columnDefinition = "char(6)")
    private CanBoYTe canBoYTe;

    @Column(name = "tieuDe", nullable = false, length = 100)
    private String tieuDe;

    @Column(name = "tomTat", nullable = false, length = 255)
    private String tomTat;

    @Lob
    @Column(name = "noiDung", nullable = false, columnDefinition = "nvarchar(max)")
    private String noiDung;

    @Column(name = "loaiTin", nullable = false, length = 50)
    private String loaiTin;

    @Column(name = "anhDaiDien", length = 255)
    private String anhDaiDien;

    @Column(name = "ngayDang", nullable = false, insertable = false, updatable = false)
    private LocalDateTime ngayDang;

    @Column(name = "trangThai", nullable = false, length = 50)
    private String trangThai;
}

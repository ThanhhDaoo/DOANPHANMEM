package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TINHTRANGSUCKHOE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinhTrangSucKhoe {

    @Id
    @Column(name = "maCapNhat", columnDefinition = "char(7)", length = 7)
    private String maCapNhat;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKhaiBao", referencedColumnName = "maKhaiBao", nullable = false, columnDefinition = "char(7)")
    private KhaiBaoYTe khaiBaoYTe;

    public String getMaKhaiBao() {
        return khaiBaoYTe != null ? khaiBaoYTe.getMaKhaiBao() : null;
    }

    @Column(name = "ngayCapNhat", nullable = false, insertable = false, updatable = false)
    private LocalDateTime ngayCapNhat;

    @Column(name = "nhietDo", nullable = false)
    private Double nhietDo;

    @Lob
    @Column(name = "trieuChungHienTai", columnDefinition = "nvarchar(max)")
    private String trieuChungHienTai;

    @Lob
    @Column(name = "ghiChu", columnDefinition = "nvarchar(max)")
    private String ghiChu;
}

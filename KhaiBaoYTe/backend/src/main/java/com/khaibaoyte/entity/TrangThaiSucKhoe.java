package com.khaibaoyte.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TRANGTHAI_SUCKHOE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrangThaiSucKhoe {

    @Id
    @Column(name = "maTrangThai", columnDefinition = "char(5)", length = 5)
    private String maTrangThai;

    @Column(name = "tenTrangThai", nullable = false, length = 100)
    private String tenTrangThai;
}

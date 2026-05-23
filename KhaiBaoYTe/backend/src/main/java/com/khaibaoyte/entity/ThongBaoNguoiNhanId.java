package com.khaibaoyte.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongBaoNguoiNhanId implements Serializable {

    @Column(name = "maThongBao", columnDefinition = "char(7)", length = 7)
    private String maThongBao;

    @Column(name = "maNguoiDan", columnDefinition = "char(6)", length = 6)
    private String maNguoiDan;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThongBaoNguoiNhanId that = (ThongBaoNguoiNhanId) o;
        return Objects.equals(maThongBao, that.maThongBao) &&
               Objects.equals(maNguoiDan, that.maNguoiDan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maThongBao, maNguoiDan);
    }
}

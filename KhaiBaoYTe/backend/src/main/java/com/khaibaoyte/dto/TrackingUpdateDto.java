package com.khaibaoyte.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingUpdateDto {
    private String maNguoiDan;
    private String maCanBo;
    private String maTrangThai;
    private String mucDoNguyCo;
    private String ghiChuChiTiet;
}

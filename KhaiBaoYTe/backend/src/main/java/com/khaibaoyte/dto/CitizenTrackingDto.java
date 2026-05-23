package com.khaibaoyte.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenTrackingDto {
    private String maNguoiDan;
    private String hoTen;
    private String maToDanPho;
    private String tenToDanPho;
    private String maTrangThai;
    private String tenTrangThai;
    private String mucDoNguyCo;
    private String ghiChuChiTiet;
    private LocalDateTime thoiGianCapNhat;
}

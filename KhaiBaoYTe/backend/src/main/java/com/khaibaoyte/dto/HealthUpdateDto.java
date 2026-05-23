package com.khaibaoyte.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthUpdateDto {
    private String maKhaiBao;
    private Double nhietDo;
    private String trieuChungHienTai;
    private String ghiChu;
}

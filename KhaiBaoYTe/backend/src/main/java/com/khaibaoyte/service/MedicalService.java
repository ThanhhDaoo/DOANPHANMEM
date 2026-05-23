package com.khaibaoyte.service;

import com.khaibaoyte.dto.CitizenTrackingDto;
import com.khaibaoyte.dto.HealthUpdateDto;
import com.khaibaoyte.dto.TrackingUpdateDto;
import com.khaibaoyte.entity.KhaiBaoYTe;
import com.khaibaoyte.entity.TheoDoiSucKhoe;
import com.khaibaoyte.entity.TinhTrangSucKhoe;
import com.khaibaoyte.entity.TrangThaiSucKhoe;

import java.util.List;

public interface MedicalService {
    // Chức năng 1: Cập nhật tình trạng sức khỏe (Người dân)
    List<KhaiBaoYTe> getDeclarationsByCitizen(String maNguoiDan);
    List<TinhTrangSucKhoe> getHealthUpdatesByCitizen(String maNguoiDan);
    TinhTrangSucKhoe createHealthUpdate(HealthUpdateDto dto);

    // Chức năng 2: Theo dõi và cập nhật trạng thái sức khỏe người bệnh (Cán bộ y tế)
    List<CitizenTrackingDto> getCitizenTrackingList();
    List<TrangThaiSucKhoe> getAllHealthStatuses();
    TheoDoiSucKhoe createTrackingUpdate(TrackingUpdateDto dto);
}

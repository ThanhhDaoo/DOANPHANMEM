package com.khaibaoyte.service.impl;

import com.khaibaoyte.dto.CitizenTrackingDto;
import com.khaibaoyte.dto.HealthUpdateDto;
import com.khaibaoyte.dto.TrackingUpdateDto;
import com.khaibaoyte.entity.*;
import com.khaibaoyte.repository.*;
import com.khaibaoyte.service.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicalServiceImpl implements MedicalService {

    @Autowired
    private KhaiBaoYTeRepository khaiBaoYTeRepository;

    @Autowired
    private TinhTrangSucKhoeRepository tinhTrangSucKhoeRepository;

    @Autowired
    private TheoDoiSucKhoeRepository theoDoiSucKhoeRepository;

    @Autowired
    private NguoiDanRepository nguoiDanRepository;

    @Autowired
    private CanBoYTeRepository canBoYTeRepository;

    @Autowired
    private TrangThaiSucKhoeRepository trangThaiSucKhoeRepository;

    // ==========================================
    // CHỨC NĂNG 1: CẬP NHẬT TÌNH TRẠNG SỨC KHỎE (NGƯỜI DÂN)
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<KhaiBaoYTe> getDeclarationsByCitizen(String maNguoiDan) {
        return khaiBaoYTeRepository.findByNguoiDanMaNguoiDanOrderByThoiGianKhaiBaoDesc(maNguoiDan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TinhTrangSucKhoe> getHealthUpdatesByCitizen(String maNguoiDan) {
        return tinhTrangSucKhoeRepository.findByKhaiBaoYTeNguoiDanMaNguoiDanOrderByNgayCapNhatDesc(maNguoiDan);
    }

    @Override
    public TinhTrangSucKhoe createHealthUpdate(HealthUpdateDto dto) {
        // Find declaration
        KhaiBaoYTe khaiBao = khaiBaoYTeRepository.findById(dto.getMaKhaiBao())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu khai báo y tế có ID: " + dto.getMaKhaiBao()));

        // Auto generate maCapNhat
        String maxId = tinhTrangSucKhoeRepository.findMaxMaCapNhat();
        String nextId = generateNextId(maxId, "CN", 7);

        // Build entity
        TinhTrangSucKhoe update = TinhTrangSucKhoe.builder()
                .maCapNhat(nextId)
                .khaiBaoYTe(khaiBao)
                .nhietDo(dto.getNhietDo())
                .trieuChungHienTai(dto.getTrieuChungHienTai())
                .ghiChu(dto.getGhiChu())
                .ngayCapNhat(LocalDateTime.now())
                .build();

        return tinhTrangSucKhoeRepository.save(update);
    }

    // ==========================================
    // CHỨC NĂNG 2: THEO DÕI VÀ CẬP NHẬT TRẠNG THÁI SỨC KHỎE (CÁN BỘ Y TẾ)
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<CitizenTrackingDto> getCitizenTrackingList() {
        // Lấy tất cả người dân (vaiTro = false)
        List<NguoiDan> citizens = nguoiDanRepository.findAll().stream()
                .filter(nd -> !nd.getVaiTro())
                .toList();

        List<CitizenTrackingDto> result = new ArrayList<>();

        for (NguoiDan nd : citizens) {
            CitizenTrackingDto dto = new CitizenTrackingDto();
            dto.setMaNguoiDan(nd.getMaNguoiDan());
            dto.setHoTen(nd.getHoTen());
            
            if (nd.getToDanPho() != null) {
                dto.setMaToDanPho(nd.getToDanPho().getMaToDanPho());
                dto.setTenToDanPho(nd.getToDanPho().getTenToDanPho());
            }

            // Tìm thông tin theo dõi sức khỏe mới nhất của người dân này
            Optional<TheoDoiSucKhoe> latestTrackOpt = theoDoiSucKhoeRepository
                    .findFirstByNguoiDanMaNguoiDanOrderByThoiGianCapNhatDesc(nd.getMaNguoiDan());

            if (latestTrackOpt.isPresent()) {
                TheoDoiSucKhoe track = latestTrackOpt.get();
                dto.setMaTrangThai(track.getTrangThaiSucKhoe().getMaTrangThai());
                dto.setTenTrangThai(track.getTrangThaiSucKhoe().getTenTrangThai());
                dto.setMucDoNguyCo(track.getMucDoNguyCo());
                dto.setGhiChuChiTiet(track.getGhiChuChiTiet());
                dto.setThoiGianCapNhat(track.getThoiGianCapNhat());
            } else {
                // Mặc định nếu chưa được cán bộ theo dõi lần nào
                dto.setMaTrangThai("TT001");
                dto.setTenTrangThai("Bình thường");
                dto.setMucDoNguyCo("Thấp");
                dto.setGhiChuChiTiet("Chưa có ghi nhận theo dõi");
                dto.setThoiGianCapNhat(null);
            }
            result.add(dto);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrangThaiSucKhoe> getAllHealthStatuses() {
        return trangThaiSucKhoeRepository.findAll();
    }

    @Override
    public TheoDoiSucKhoe createTrackingUpdate(TrackingUpdateDto dto) {
        // Tìm người dân cần cập nhật
        NguoiDan nd = nguoiDanRepository.findById(dto.getMaNguoiDan())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dân có ID: " + dto.getMaNguoiDan()));

        // Tìm cán bộ cập nhật
        CanBoYTe cb = canBoYTeRepository.findById(dto.getMaCanBo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ cán bộ y tế có ID: " + dto.getMaCanBo()));

        // Tìm trạng thái sức khỏe
        TrangThaiSucKhoe status = trangThaiSucKhoeRepository.findById(dto.getMaTrangThai())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã trạng thái sức khỏe: " + dto.getMaTrangThai()));

        // Tự sinh mã theo dõi
        String maxId = theoDoiSucKhoeRepository.findMaxMaTheoDoi();
        String nextId = generateNextId(maxId, "TD", 7);

        // Build entity
        TheoDoiSucKhoe track = TheoDoiSucKhoe.builder()
                .maTheoDoi(nextId)
                .nguoiDan(nd)
                .canBoYTe(cb)
                .trangThaiSucKhoe(status)
                .mucDoNguyCo(dto.getMucDoNguyCo())
                .ghiChuChiTiet(dto.getGhiChuChiTiet())
                .thoiGianCapNhat(LocalDateTime.now())
                .build();

        return theoDoiSucKhoeRepository.save(track);
    }

    // ==========================================
    // UTILITY LOGIC: ID AUTO-GENERATOR
    // ==========================================
    
    private String generateNextId(String maxId, String prefix, int totalLength) {
        if (maxId == null || maxId.trim().isEmpty()) {
            // Mặc định giá trị ban đầu (ví dụ: CN00001, TD00001)
            int numericLength = totalLength - prefix.length();
            return prefix + String.format("%0" + numericLength + "d", 1);
        }
        
        // Cắt bỏ prefix để lấy phần số
        String numericPart = maxId.trim().substring(prefix.length());
        try {
            int currentVal = Integer.parseInt(numericPart);
            int nextVal = currentVal + 1;
            int numericLength = totalLength - prefix.length();
            return prefix + String.format("%0" + numericLength + "d", nextVal);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Lỗi định dạng khóa chính khi tự sinh ID: " + maxId, e);
        }
    }
}

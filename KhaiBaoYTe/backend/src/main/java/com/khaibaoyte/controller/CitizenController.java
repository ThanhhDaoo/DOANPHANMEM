package com.khaibaoyte.controller;

import com.khaibaoyte.dto.HealthUpdateDto;
import com.khaibaoyte.entity.KhaiBaoYTe;
import com.khaibaoyte.entity.TinhTrangSucKhoe;
import com.khaibaoyte.service.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/citizen")
@CrossOrigin(origins = "*") // Cho phép gọi API trực tiếp từ frontend local
public class CitizenController {

    @Autowired
    private MedicalService medicalService;

    // Lấy danh sách tờ khai y tế liên quan của người dân
    @GetMapping("/declarations")
    public ResponseEntity<List<KhaiBaoYTe>> getDeclarations(@RequestParam String maNguoiDan) {
        List<KhaiBaoYTe> list = medicalService.getDeclarationsByCitizen(maNguoiDan);
        return ResponseEntity.ok(list);
    }

    // Lấy lịch sử cập nhật sức khoẻ của người dân
    @GetMapping("/health-updates")
    public ResponseEntity<List<TinhTrangSucKhoe>> getHealthUpdates(@RequestParam String maNguoiDan) {
        List<TinhTrangSucKhoe> list = medicalService.getHealthUpdatesByCitizen(maNguoiDan);
        return ResponseEntity.ok(list);
    }

    // Gửi phiếu cập nhật sức khoẻ mới
    @PostMapping("/health-updates")
    public ResponseEntity<Map<String, Object>> saveHealthUpdate(@RequestBody HealthUpdateDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            TinhTrangSucKhoe result = medicalService.createHealthUpdate(dto);
            response.put("status", "SUCCESS");
            response.put("message", "Đã lưu cập nhật tình trạng sức khoẻ thành công!");
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi lưu cập nhật sức khoẻ: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

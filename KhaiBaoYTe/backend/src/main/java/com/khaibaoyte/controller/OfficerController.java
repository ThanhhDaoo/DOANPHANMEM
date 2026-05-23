package com.khaibaoyte.controller;

import com.khaibaoyte.dto.CitizenTrackingDto;
import com.khaibaoyte.dto.TrackingUpdateDto;
import com.khaibaoyte.entity.TheoDoiSucKhoe;
import com.khaibaoyte.entity.TrangThaiSucKhoe;
import com.khaibaoyte.service.MedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/officer")
@CrossOrigin(origins = "*") // Cho phép gọi API trực tiếp từ frontend local
public class OfficerController {

    @Autowired
    private MedicalService medicalService;

    // Lấy danh sách người dân kèm thông tin theo dõi sức khoẻ mới nhất
    @GetMapping("/tracking")
    public ResponseEntity<List<CitizenTrackingDto>> getCitizenTrackingList() {
        List<CitizenTrackingDto> list = medicalService.getCitizenTrackingList();
        return ResponseEntity.ok(list);
    }

    // Lấy danh mục các trạng thái sức khoẻ của hệ thống
    @GetMapping("/health-statuses")
    public ResponseEntity<List<TrangThaiSucKhoe>> getAllHealthStatuses() {
        List<TrangThaiSucKhoe> list = medicalService.getAllHealthStatuses();
        return ResponseEntity.ok(list);
    }

    // Cán bộ y tế cập nhật trạng thái/mức nguy cơ mới của người dân
    @PostMapping("/tracking")
    public ResponseEntity<Map<String, Object>> saveTrackingUpdate(@RequestBody TrackingUpdateDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            TheoDoiSucKhoe result = medicalService.createTrackingUpdate(dto);
            response.put("status", "SUCCESS");
            response.put("message", "Đã cập nhật theo dõi sức khoẻ thành công!");
            response.put("data", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "ERROR");
            response.put("message", "Lỗi khi cập nhật theo dõi: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

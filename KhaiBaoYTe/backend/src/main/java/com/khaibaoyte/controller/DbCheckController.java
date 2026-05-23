package com.khaibaoyte.controller;

import com.khaibaoyte.entity.NguoiDan;
import com.khaibaoyte.entity.ToDanPho;
import com.khaibaoyte.repository.BaiDangTinTucRepository;
import com.khaibaoyte.repository.KhaiBaoYTeRepository;
import com.khaibaoyte.repository.NguoiDanRepository;
import com.khaibaoyte.repository.ToDanPhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/db-check")
public class DbCheckController {

    @Autowired
    private ToDanPhoRepository toDanPhoRepository;

    @Autowired
    private NguoiDanRepository nguoiDanRepository;

    @Autowired
    private KhaiBaoYTeRepository khaiBaoYTeRepository;

    @Autowired
    private BaiDangTinTucRepository baiDangTinTucRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> checkDatabase() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            long toDanPhoCount = toDanPhoRepository.count();
            long nguoiDanCount = nguoiDanRepository.count();
            long khaiBaoCount = khaiBaoYTeRepository.count();
            long tinTucCount = baiDangTinTucRepository.count();

            result.put("status", "SUCCESS");
            result.put("message", "Kết nối Cơ sở dữ liệu thành công!");
            
            Map<String, Long> counts = new HashMap<>();
            counts.put("TODANPHO (Tổ dân phố)", toDanPhoCount);
            counts.put("NGUOIDAN (Người dân/Cán bộ)", nguoiDanCount);
            counts.put("KHAIBAOYTE (Khai báo y tế)", khaiBaoCount);
            counts.put("BAIDANGTINTUC (Tin tức)", tinTucCount);
            result.put("statistics", counts);

            // Fetch list of ToDanPho
            List<Map<String, String>> toDanPhos = toDanPhoRepository.findAll().stream()
                    .map(tdp -> {
                        Map<String, String> m = new HashMap<>();
                        m.put("maToDanPho", tdp.getMaToDanPho());
                        m.put("tenToDanPho", tdp.getTenToDanPho());
                        m.put("moTa", tdp.getMoTa());
                        return m;
                    })
                    .collect(Collectors.toList());
            result.put("toDanPhoList", toDanPhos);

            // Fetch list of NguoiDan
            List<Map<String, Object>> nguoiDans = nguoiDanRepository.findAll().stream()
                    .map(nd -> {
                        Map<String, Object> m = new HashMap<>();
                        m.put("maNguoiDan", nd.getMaNguoiDan());
                        m.put("hoTen", nd.getHoTen());
                        m.put("soDienThoai", nd.getSoDienThoai());
                        m.put("vaiTro", nd.getVaiTro() ? "Cán bộ/Admin" : "Người dân");
                        m.put("trangThai", nd.getTrangThai());
                        return m;
                    })
                    .collect(Collectors.toList());
            result.put("nguoiDanList", nguoiDans);

        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", "Không thể truy vấn CSDL: " + e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.ok(result);
    }
}

package com.khaibaoyte.repository;

import com.khaibaoyte.entity.KhaiBaoYTe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface KhaiBaoYTeRepository extends JpaRepository<KhaiBaoYTe, String> {
    List<KhaiBaoYTe> findByNguoiDanMaNguoiDanOrderByThoiGianKhaiBaoDesc(String maNguoiDan);
}

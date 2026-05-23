package com.khaibaoyte.repository;

import com.khaibaoyte.entity.NguoiDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NguoiDanRepository extends JpaRepository<NguoiDan, String> {
    Optional<NguoiDan> findByCccd(String cccd);
    Optional<NguoiDan> findBySoDienThoai(String soDienThoai);
}

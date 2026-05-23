package com.khaibaoyte.repository;

import com.khaibaoyte.entity.CanBoYTe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CanBoYTeRepository extends JpaRepository<CanBoYTe, String> {
    Optional<CanBoYTe> findByNguoiDanMaNguoiDan(String maNguoiDan);
}

package com.khaibaoyte.repository;

import com.khaibaoyte.entity.BaiDangTinTuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BaiDangTinTucRepository extends JpaRepository<BaiDangTinTuc, String> {
    List<BaiDangTinTuc> findByTrangThaiOrderByNgayDangDesc(String trangThai);
}

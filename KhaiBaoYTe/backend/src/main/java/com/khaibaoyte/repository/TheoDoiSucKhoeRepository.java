package com.khaibaoyte.repository;

import com.khaibaoyte.entity.TheoDoiSucKhoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TheoDoiSucKhoeRepository extends JpaRepository<TheoDoiSucKhoe, String> {

    @Query("SELECT MAX(t.maTheoDoi) FROM TheoDoiSucKhoe t")
    String findMaxMaTheoDoi();

    Optional<TheoDoiSucKhoe> findFirstByNguoiDanMaNguoiDanOrderByThoiGianCapNhatDesc(String maNguoiDan);

    @Query("SELECT t FROM TheoDoiSucKhoe t WHERE t.thoiGianCapNhat = (" +
           "SELECT MAX(t2.thoiGianCapNhat) FROM TheoDoiSucKhoe t2 WHERE t2.nguoiDan.maNguoiDan = t.nguoiDan.maNguoiDan" +
           ")")
    List<TheoDoiSucKhoe> findLatestTrackingForAll();
}

package com.khaibaoyte.repository;

import com.khaibaoyte.entity.TinhTrangSucKhoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TinhTrangSucKhoeRepository extends JpaRepository<TinhTrangSucKhoe, String> {
    
    List<TinhTrangSucKhoe> findByKhaiBaoYTeNguoiDanMaNguoiDanOrderByNgayCapNhatDesc(String maNguoiDan);

    @Query("SELECT MAX(t.maCapNhat) FROM TinhTrangSucKhoe t")
    String findMaxMaCapNhat();
}

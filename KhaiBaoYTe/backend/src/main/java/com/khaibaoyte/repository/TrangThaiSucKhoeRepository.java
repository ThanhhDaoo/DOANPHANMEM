package com.khaibaoyte.repository;

import com.khaibaoyte.entity.TrangThaiSucKhoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrangThaiSucKhoeRepository extends JpaRepository<TrangThaiSucKhoe, String> {
}

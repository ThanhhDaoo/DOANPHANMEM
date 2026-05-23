package com.khaibaoyte.repository;

import com.khaibaoyte.entity.ToDanPho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDanPhoRepository extends JpaRepository<ToDanPho, String> {
}

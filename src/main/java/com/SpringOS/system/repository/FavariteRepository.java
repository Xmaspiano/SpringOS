package com.SpringOS.system.repository;

import com.SpringOS.system.entity.Favarite;
import com.SpringOS.system.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface FavariteRepository
        extends JpaRepository<Favarite, Long>,CommonRepository<Favarite, FavariteRepository> {

   public Favarite findByUserIdAndMenuId(Long userId, Long menuId);

   public List<Favarite> findByUserId(Long userId);
}

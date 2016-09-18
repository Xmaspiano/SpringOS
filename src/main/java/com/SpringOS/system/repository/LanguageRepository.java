package com.SpringOS.system.repository;

import com.SpringOS.system.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface LanguageRepository
        extends JpaRepository<Language, Long>,CommonRepository<Language, LanguageRepository> {

    public Language findByName(String name);
}

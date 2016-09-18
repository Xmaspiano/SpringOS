package com.SpringOS.system.repository;

import com.SpringOS.system.entity.Language;
import com.SpringOS.system.entity.LanguageEnUS;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface LanguageEnUSRepository
        extends JpaRepository<LanguageEnUS, Long>,CommonRepository<LanguageEnUS, LanguageEnUSRepository> {

    public Language findByName(String name);
}

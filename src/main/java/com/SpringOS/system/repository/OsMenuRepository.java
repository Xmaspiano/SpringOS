package com.SpringOS.system.repository;

import com.SpringOS.system.entity.OsMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface OsMenuRepository extends JpaRepository<OsMenu,Long>, CommonRepository<OsMenu, OsMenuRepository> {

    List<OsMenu> findByParentid(long parentid);

    List<OsMenu> findAllByLife(Boolean life);
}

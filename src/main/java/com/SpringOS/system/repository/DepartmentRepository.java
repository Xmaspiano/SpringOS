package com.SpringOS.system.repository;

import com.SpringOS.system.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface DepartmentRepository extends JpaRepository<Department,Long>, CommonRepository<Department, DepartmentRepository> {

    List<Department> findByParentid(long parentid);

    List<Department> findAllByLife(Boolean life);
}

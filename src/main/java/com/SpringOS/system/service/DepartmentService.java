package com.SpringOS.system.service;

import com.SpringOS.system.entity.Department;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface DepartmentService extends CommonService<Department>{

    public List<Department> findAllBySuper(long id);

    public List<Department> findAllRealLife();

}

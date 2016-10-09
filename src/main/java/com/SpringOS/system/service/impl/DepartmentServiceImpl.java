package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.Department;
import com.SpringOS.system.repository.DepartmentRepository;
import com.SpringOS.system.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */
@Service
@Transactional
public class DepartmentServiceImpl
        extends CommonServiceImpl<Department, DepartmentRepository>
        implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipUserRoleServiceImpl.class);

    @Override
    @Cacheable(value = "meun_data", key = "#id")//先从缓存中读取，如果没有再调用方法获取数据，然后把数据添加到缓存中
    public Department findOne(Long id) {
        return super.findOne(id);
    }

    @Override
    @CachePut(value = "meun_data", key = "#department.id")//数据放入缓存
    public Department save(Department department) {
        return super.save(department);
    }

    @Override
    @CacheEvict(value = "meun_data", key = "#id") //移除指定key的数据
    public void delete(Long id) {
        super.delete(id);
    }

    public List<Department> findAllBySuper(long id){
        return getRepository().findByParentid(id);
    }

    public List<Department> findAllRealLife(){
        return getRepository().findAllByLife(true);
    }

    private void fullDepartment(Department department){

    }

    private List<Department> test(List<Department> DepartmentList){
        Department department;
        List<Department> liTemp;
        for (int i = 0; i < DepartmentList.size(); i++) {
            department = DepartmentList.get(i);
            liTemp = getRepository().findByParentid(department.getId());
            if(liTemp.size()>0) {
                department.setDepartmentList(test(liTemp));
            }
        }
        return DepartmentList;
    }

}

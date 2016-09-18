package com.SpringOS.system.service.impl;


import com.SpringOS.system.repository.CommonRepository;
import com.SpringOS.system.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public abstract class CommonServiceImpl<E, R extends CrudRepository> implements CommonService<E> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private CommonRepository<E,R> commonRepository;

    @Override
    public E findOne(Long id) {
        return commonRepository.findOne(id);
    }

    @Override
    public List<E> findAll() {
        return (List<E>)commonRepository.findAll();
    }

    @Override
    public E save(E e){
        return commonRepository.save(e);
    }

    @Override
    public Iterable<E> save(Iterable<E> entities){
        return commonRepository.save(entities);
    }

    @Override
    public void delete(Long id) {
        commonRepository.delete(commonRepository.findOne(id));
    }

    public R getRepository() {
        return (R) commonRepository;
    }

}

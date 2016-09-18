package com.SpringOS.system.service;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

/****
 *
 * @param <E> entity
 */
public interface CommonService <E> {

    public E findOne(Long id);

    public List<E> findAll();

    public E save(E e);

    public Iterable<E> save(Iterable<E> entities);

    public void delete(Long id);
}

package com.SpringOS.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by AlbertXmas on 16/8/8.
 */

/***
 *
 * @param <R> repository
 */
@NoRepositoryBean
@Component
public interface CommonRepository<E, R> extends CrudRepository<E, Long> {

}

package com.SpringOS.system.service.impl;

import com.SpringOS.system.entity.Language;
import com.SpringOS.system.repository.LanguageRepository;
import com.SpringOS.system.service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl
        extends CommonServiceImpl<Language, LanguageRepository>
        implements LanguageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);

    @Override
    @CachePut(value = "jsp-tagCache", key = "#language.name")//数据放入缓存
    public Language save(Language language) {
        return super.save(language);
    }

    @Override
    public Language findOne(Long id) {
        return super.findOne(id);
    }

    @Override
    @Cacheable(value = "jsp-tagCache", key = "#name")//先从缓存中读取，如果没有再调用方法获取数据，然后把数据添加到缓存中
    public Language findByName(String name) {
        return getRepository().findByName(name);
    }

    @CacheEvict(value = "jsp-tagCache", key = "#name") //移除指定key的数据
    public void seleteByName(String name){
        delete(findByName(name).getId());
    }
}

package com.SpringOS.system.service;


import com.SpringOS.system.entity.Favarite;

import java.util.List;

public interface FavariteService extends CommonService<Favarite> {

    public Favarite findByUserIdAndMenuId(Long userId, Long menuId);

    public void deleteByUserIdAndMenuId(Long userId, Long menuId);

    public List<Favarite> findByUserId(Long userId);
}

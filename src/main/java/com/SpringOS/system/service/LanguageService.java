package com.SpringOS.system.service;


import com.SpringOS.system.entity.Language;

import java.util.List;
import java.util.Set;

public interface LanguageService extends CommonService<Language> {

    public Language findByName(String name);
}

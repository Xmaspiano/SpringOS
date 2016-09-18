package com.SpringOS.util;

import com.SpringOS.system.entity.Language;
import com.SpringOS.system.service.LanguageService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by AlbertXmas on 16/9/7.
 */
public class LanguageInfoTag extends TagSupport {

    private String name;

    private LanguageService languageService;

    private CacheManager cacheManager;

    private WebApplicationContext webApplicationContext;

    private String page_language;
    private String BeanServiceName;
    private String CacheName;

    private LanguageType lType;

    public LanguageInfoTag(){
        super();
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        cacheManager = (CacheManager) webApplicationContext.getBean("springCacheManager");
    }
    @Override
    public int doStartTag() throws JspException {
        try {
            cacheInit();

            /*获取bean*/
            if(languageService == null){
                languageService = (LanguageService) webApplicationContext.getBean(BeanServiceName);
            }

            Cache cache = cacheManager.getCache(CacheName);

            Language lag = cache.get(name, Language.class);
            if(lag == null){
                lag = languageService.findByName(name);

                if(lag == null) {
                    lag = new Language();
                }

                lag.setRealPath(pageContext.getAttribute("AppId").toString());
                lag.setName(name);

                languageService.save(lag);
            }
            JspWriter out = this.pageContext.getOut();
            if(name == null) {
                name = "未初始化名称";
            }
            out.print(lag.getName());
        } catch(Exception e) {
            e.printStackTrace();
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;

    }


    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override

    public void release() {
        super.release();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void cacheInit(){
        page_language = pageContext.getRequest().getLocale().toString();
        switch(getLanguageValue(page_language)) {
            case zh_CN:
                BeanServiceName = "languageServiceImpl";
                CacheName = "jsp-tagCache";
                lType = LanguageType.zh_CN;
                break;
            case en_US:
                BeanServiceName = "languageServiceImpl";
                CacheName = "jsp-tagCache-en_US";
                lType = LanguageType.en_US;
                break;
            default:
                BeanServiceName = "languageServiceImpl";
                CacheName = "jsp-tagCache";
                lType = LanguageType.zh_CN;
        }
    }

    public LanguageType getLanguageValue(String str){
        for(LanguageType languageType: LanguageType.values()){
            if(str.equals(languageType.toString())){
                return languageType;
            }
        }
        return LanguageType.zh_CN;
    }

    public enum LanguageType {
        zh_CN("zh_CN"),
        en_US("en_US")
        ;

        private final String info;

        private LanguageType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }

}
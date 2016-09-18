package com.SpringOS;

import com.SpringOS.system.entity.common.IdEntity;
import com.SpringOS.util.PackageUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by AlbertXmas on 16/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "file:src/main/resources/applicationContext.xml","file:src/main/webapp/WEB-INF/spring-mvc.xml"
})
//@TestExecutionListeners({TransactionalTestExecutionListener.class, SqlScriptsTestExecutionListener.class})
//@Transactional
public class BasicTest {//} extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;

    @Before
    public void setup() throws Exception {

        this.mockMvc = webAppContextSetup(this.wac).build();

    }

    @Test
    public void init(){
        List authList = new ArrayList();

        List<Class<?>> classes = new PackageUtil().getClasses("com.SpringOS");
        RequiresRoles rr;
        shiroSubject ss;
        for (Class clas :classes) {
            Method[] md = clas.getMethods();
            for(Method method: md){
                rr = method.getAnnotation(RequiresRoles.class);
                if(rr != null && !rr.value().equals("")){
                    ss = new shiroSubject();
                    ss.realName = clas.getName();
                    ss.method = method.getName();
                    for(String s: rr.value()){
                        ss.shiroAuth += s;
                    }
                    authList.add(ss);
                    break;
                }
            }
            System.out.println(authList);
        }
    }

    class shiroSubject extends IdEntity{
        String realName = "";
        String method = "";
        String shiroAuth = "";


    }
}

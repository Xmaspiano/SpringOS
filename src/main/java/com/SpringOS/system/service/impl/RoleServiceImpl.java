package com.SpringOS.system.service.impl;

        import com.SpringOS.system.entity.Role;
        import com.SpringOS.system.entity.User;
        import com.SpringOS.system.repository.RoleRepository;
        import com.SpringOS.system.repository.UserRepository;
        import com.SpringOS.system.service.RoleService;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class RoleServiceImpl
        extends CommonServiceImpl<Role, RoleRepository>
        implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

}

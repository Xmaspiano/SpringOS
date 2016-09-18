package com.SpringOS.system.service;


import com.SpringOS.system.entity.Organization;

import java.util.List;

public interface OrganizationService extends CommonService<Organization> {

    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}

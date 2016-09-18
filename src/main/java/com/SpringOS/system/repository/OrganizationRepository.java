package com.SpringOS.system.repository;

import com.SpringOS.system.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by AlbertXmas on 16/8/8.
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long>,CommonRepository<Organization, OrganizationRepository> {

}

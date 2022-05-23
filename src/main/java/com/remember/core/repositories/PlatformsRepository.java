package com.remember.core.repositories;

import com.remember.core.domains.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "platforms", path = "platforms")
public interface PlatformsRepository extends JpaRepository<Platform, Long> {
}

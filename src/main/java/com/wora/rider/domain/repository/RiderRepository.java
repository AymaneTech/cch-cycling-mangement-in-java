package com.wora.rider.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.valueObject.RiderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, RiderId> {
}

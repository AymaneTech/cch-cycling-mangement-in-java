package com.wora.rider.domain.repository;

import com.wora.common.domain.repository.CustomJpaRepository;
import com.wora.rider.domain.entity.Rider;
import com.wora.rider.domain.valueObject.RiderId;

public interface RiderRepository extends CustomJpaRepository<Rider, RiderId> {
}

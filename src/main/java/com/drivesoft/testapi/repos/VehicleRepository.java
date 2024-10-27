package com.drivesoft.testapi.repos;

import com.drivesoft.testapi.entities.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicles, Long> {
    List<Vehicles> findAll();

    boolean existsByAcctIdAndCollateralStockNumber(Long acctId, String collateralStockNumber);


}


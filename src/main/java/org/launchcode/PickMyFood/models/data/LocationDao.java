package org.launchcode.PickMyFood.models.data;

import org.launchcode.PickMyFood.models.Location;
import org.launchcode.PickMyFood.models.data.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LocationDao extends CrudRepository<Location, Integer> {
    List<Location> findAllByUserId(Long user_id);
}

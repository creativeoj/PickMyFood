package org.launchcode.PickMyFood.models.data;

import org.launchcode.PickMyFood.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MenuDao extends CrudRepository<Menu, Integer> {
    List<Menu> findAllByUserId(Long user_id);
}

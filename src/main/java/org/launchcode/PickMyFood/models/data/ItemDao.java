package org.launchcode.PickMyFood.models.data;

import org.launchcode.PickMyFood.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ItemDao extends CrudRepository<Item, Integer> {
    List<Item> findAllByUserId(Long user_id);
}

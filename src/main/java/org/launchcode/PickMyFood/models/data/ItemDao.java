package org.launchcode.PickMyFood.models.data;

import org.launchcode.PickMyFood.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ItemDao extends JpaRepository<Item, Integer> {
    List<Item> findAllByUserId(Long user_id);

}

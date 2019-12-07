package org.launchcode.PickMyFood.models.data;


import org.launchcode.PickMyFood.models.Histories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface HistoryDao extends JpaRepository<Histories, Integer> {
    List<Histories> findAllByUserId(Long user_id);
}
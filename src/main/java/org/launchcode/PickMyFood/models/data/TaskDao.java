package org.launchcode.PickMyFood.models.data;


import org.launchcode.PickMyFood.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

;

@Repository
@Transactional
public interface TaskDao extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(Long user_id);
}

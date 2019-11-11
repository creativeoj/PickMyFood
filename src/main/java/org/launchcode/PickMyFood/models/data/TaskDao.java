package org.launchcode.PickMyFood.models.data;


import org.launchcode.PickMyFood.models.Task;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TaskDao extends CrudRepository<Task, Integer> {
    List<Task> findAllByUserId(Long user_id);
}

package com.davidauz.library_module.repository.mysqlRepos;

import com.davidauz.library_module.entity.mysqlentity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface actorRepo extends JpaRepository<Actor, Long> {
}

package com.davidauz.library_module.repository.mssqlRepos;

import com.davidauz.library_module.entity.mssqlentity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface movieRepo extends JpaRepository<Movie, Long> {

}


package edu.ac.jsr354example.repository;

import edu.ac.jsr354example.domain.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ValueRepository extends CrudRepository<Value, Long> {

    @Query("select v from Value v where v.id = (select max(id) from Value)")
    Value pull();
}
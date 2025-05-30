package org.digitalLibrary.repository.jpa;

import org.digitalLibrary.entities.output.BookOutputEntity;
import org.digitalLibrary.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookjpaRepository extends JpaRepository<BookOutputEntity,Long> {
      List<BookOutputEntity> findAll();
}

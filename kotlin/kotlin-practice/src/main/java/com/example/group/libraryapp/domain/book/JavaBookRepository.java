package com.example.group.libraryapp.domain.book;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JavaBookRepository extends JpaRepository<JavaBook, Long> {

  Optional<JavaBook> findByName(String bookName);

}

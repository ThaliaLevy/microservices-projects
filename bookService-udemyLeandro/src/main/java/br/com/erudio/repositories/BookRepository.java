package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}

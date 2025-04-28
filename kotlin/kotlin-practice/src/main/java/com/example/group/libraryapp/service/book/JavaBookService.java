package com.example.group.libraryapp.service.book;

import com.example.group.libraryapp.domain.book.JavaBook;
import com.example.group.libraryapp.domain.book.JavaBookRepository;
import com.example.group.libraryapp.domain.user.JavaUser;
import com.example.group.libraryapp.domain.user.JavaUserRepository;
import com.example.group.libraryapp.domain.user.loanhistory.JavaUserLoanHistoryRepository;
import com.example.group.libraryapp.dto.book.request.JavaBookRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JavaBookService {

  private final JavaBookRepository bookRepository;
  private final JavaUserRepository javaUserRepository;
  private final JavaUserLoanHistoryRepository userLoanHistoryRepository;

  public JavaBookService(
      JavaBookRepository bookRepository,
      JavaUserRepository javaUserRepository,
      JavaUserLoanHistoryRepository userLoanHistoryRepository
  ) {
    this.bookRepository = bookRepository;
    this.javaUserRepository = javaUserRepository;
    this.userLoanHistoryRepository = userLoanHistoryRepository;
  }

  @Transactional
  public void saveBook(JavaBookRequest request) {
    JavaBook newBook = new JavaBook(request.getName());
    bookRepository.save(newBook);
  }

  @Transactional
  public void loanBook(BookLoanRequest request) {
    JavaBook book = bookRepository.findByName(request.getBookName())
        .orElseThrow(IllegalArgumentException::new);
    if (userLoanHistoryRepository.findByBookNameAndIsReturn(request.getBookName(), false) != null) {
      throw new IllegalArgumentException("진작 대출되어 있는 책입니다");
    }

    JavaUser javaUser = javaUserRepository.findByName(request.getUserName())
        .orElseThrow(IllegalArgumentException::new);
    javaUser.loanBook(book);
  }

  @Transactional
  public void returnBook(BookReturnRequest request) {
    JavaUser javaUser = javaUserRepository.findByName(request.getUserName())
        .orElseThrow(IllegalArgumentException::new);
    javaUser.returnBook(request.getBookName());
  }

}

package com.example.group.libraryapp.controller.book;

import com.example.group.libraryapp.dto.book.request.JavaBookRequest;
import com.group.libraryapp.dto.book.request.BookLoanRequest;
import com.group.libraryapp.dto.book.request.BookReturnRequest;
import com.example.group.libraryapp.service.book.JavaBookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaBookController {

  private final JavaBookService bookService;

  public JavaBookController(JavaBookService bookService) {
    this.bookService = bookService;
  }

  @PostMapping("/book")
  public void saveBook(@RequestBody JavaBookRequest request) {
    bookService.saveBook(request);
  }

  @PostMapping("/book/loan")
  public void loanBook(@RequestBody BookLoanRequest request) {
    bookService.loanBook(request);
  }

  @PutMapping("/book/return")
  public void returnBook(@RequestBody BookReturnRequest request) {
    bookService.returnBook(request);
  }

}

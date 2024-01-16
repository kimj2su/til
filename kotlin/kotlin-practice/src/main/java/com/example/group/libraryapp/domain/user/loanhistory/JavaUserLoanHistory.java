package com.example.group.libraryapp.domain.user.loanhistory;

import com.example.group.libraryapp.domain.user.JavaUser;
import jakarta.persistence.JoinColumn;
import org.jetbrains.annotations.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class JavaUserLoanHistory {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private JavaUser javaUser;

  private String bookName;

  private boolean isReturn;

  public JavaUserLoanHistory() {

  }

  public JavaUserLoanHistory(JavaUser javaUser, String bookName, boolean isReturn) {
    this.javaUser = javaUser;
    this.bookName = bookName;
    this.isReturn = isReturn;
  }

  @NotNull
  public String getBookName() {
    return this.bookName;
  }

  public void doReturn() {
    this.isReturn = true;
  }

  @NotNull
  public JavaUser getUser() {
    return javaUser;
  }

  public boolean isReturn() {
    return isReturn;
  }
}

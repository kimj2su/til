package com.example.group.libraryapp.domain.user;

import com.example.group.libraryapp.domain.book.JavaBook;
import com.example.group.libraryapp.domain.user.loanhistory.JavaUserLoanHistory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Entity
public class JavaUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(nullable = false)
  private String name;

  private Integer age;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<JavaUserLoanHistory> userLoanHistories = new ArrayList<>();

  public JavaUser() {

  }

  public JavaUser(String name, Integer age) {
    if (name.isBlank()) {
      throw new IllegalArgumentException("이름은 비어 있을 수 없습니다");
    }
    this.name = name;
    this.age = age;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void loanBook(JavaBook book) {
    this.userLoanHistories.add(new JavaUserLoanHistory(this, book.getName(), false));
  }

  public void returnBook(String bookName) {
    JavaUserLoanHistory targetHistory = this.userLoanHistories.stream()
        .filter(history -> history.getBookName().equals(bookName))
        .findFirst()
        .orElseThrow();
    targetHistory.doReturn();
  }

  @NotNull
  public String getName() {
    return name;
  }

  @Nullable
  public Integer getAge() {
    return age;
  }

  public Long getId() {
    return id;
  }

}

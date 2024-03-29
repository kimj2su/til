package com.example.group.libraryapp.dto.book.request;

public class JavaBookReturnRequest {

    private String userName;
    private String bookName;

    public JavaBookReturnRequest(String userName, String bookName) {
        this.userName = userName;
        this.bookName = bookName;
    }

    public String getUserName() {
        return userName;
    }

    public String getBookName() {
        return bookName;
    }

}

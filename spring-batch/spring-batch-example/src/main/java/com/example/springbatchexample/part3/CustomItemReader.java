package com.example.springbatchexample.part3;

/*
자바 컬렉션 아이템을 리더로 처리하는 커스텀 아이템리더
 */

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

public class CustomItemReader<T> implements ItemReader<T> {

    private final List<T> items;

    public CustomItemReader(List<T> items) {
        this.items = new ArrayList<>(items);
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!items.isEmpty()) {
            //remove는 첫번쨰 아이템을 반환해주면서 제거한다.
            return items.remove(0);
        }

        return null;
    }
}

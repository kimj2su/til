package sample.cafekiosk.leaning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class GuavaLearningTest {

    @DisplayName("주어진 개수만큼 List 파티셔닝을 한다.")
    @Test
    void partitionLearningTest1() {
        // given : 선행조건 기술
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when : 기능 수행
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then : 결과 확인
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1, 2, 3),
                        List.of(4, 5, 6)
                ));
    }

    @DisplayName("주어진 개수만큼 List 파티셔닝을 한다.")
    @Test
    void partitionLearningTest2() {
        // given : 선행조건 기술
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when : 기능 수행
        List<List<Integer>> partition = Lists.partition(integers, 4);

        // then : 결과 확인
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1, 2, 3, 4),
                        List.of(5, 6)
                ));
    }

    @DisplayName("멀티맵 기능확인")
    @Test
    void multiMapLearningTest() {
        // given : 선행조건 기술
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라뗴");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크로아상");
        multimap.put("베이커리", "식빵");

        // when : 기능 수행
        Collection<String> strings = multimap.get("커피");

        // then : 결과 확인
        assertThat(strings).hasSize(3)
                .isEqualTo(List.of("아메리카노", "카페라뗴", "카푸치노"));
    }

    @DisplayName("멀티맵 기능확인")
    @TestFactory
    Collection<DynamicTest> multiMapLearningTest2() {
        // given : 선행조건 기술
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라뗴");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "크로아상");
        multimap.put("베이커리", "식빵");

        return List.of(
                DynamicTest.dynamicTest("1개 value 삭제", () -> {
                    // when : 기능 수행
                    multimap.remove("커피", "아메리카노");

                    // then : 결과 확인
                    Collection<String> results = multimap.get("커피");
                    assertThat(results).hasSize(2)
                            .isEqualTo(List.of("카페라뗴", "카푸치노"));
                }),
                DynamicTest.dynamicTest("1개 key 삭제", () -> {
                    // when : 기능 수행
                    multimap.removeAll("커피");

                    // then : 결과 확인
                    Collection<String> results = multimap.get("커피");
                    assertThat(multimap.get("커피")).isEmpty();
                })
        );
    }
}

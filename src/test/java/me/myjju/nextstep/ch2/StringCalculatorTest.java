package me.myjju.nextstep.ch2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StringCalculatorTest {

//    @Test
//    void integerTest() {
//        int i = Integer.parseUnsignedInt("1");
//        System.out.println("i = " + i);
//    }

    private StringCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new StringCalculator();
    }

    @Test
    void add_null_또는_빈문자() {
        assertThat(calculator.add(" ")).isEqualTo(0);
        assertThat(calculator.add(null)).isEqualTo(0);
    }

    @Test
    void add_숫자하나() {
        assertThat(calculator.add("3")).isEqualTo(3);
    }

    @Test
    void add_쉼표구분자() {
        assertThat(calculator.add("1,2")).isEqualTo(3);
        assertThat(calculator.add("1,2,3")).isEqualTo(6);
    }

    @Test
    void add_쉼표_또는_콜론_구분자() {
        assertThat(calculator.add("1,2:3")).isEqualTo(6);
    }

    @Test
    void add_custom_구분자() {

        assertThat(calculator.add("//;\n1;2;3")).isEqualTo(6);
        assertThat(calculator.add("//*\n1*2*3")).isEqualTo(6);
    }

    @Test
    void add_custom_쉼표_콜론_구분자() {

        assertThat(calculator.add("//;\n1;2;3")).isEqualTo(6);
        assertThat(calculator.add("//*\n1*2:3,4")).isEqualTo(10);
    }

    @Test
    void 알수없는_구분자_0처리() {

        assertThat(calculator.add("//*\n1*2#3")).isEqualTo(0);
    }

    @Test
    void 음수는_예외처리() {
        assertThatThrownBy(() -> calculator.add("1,-1")).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> calculator.add("//;\n1;-1;8")).isInstanceOf(RuntimeException.class);
    }

    @Test
    void split_test() {
        String[] split = "1*2;3,4,".split("\\,|\\:");
        for (String s : split) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void empty한_Integer컬렉션의_합의_결과는_0이다() {
        List<Integer> list = Collections.emptyList();
        int sum = list.stream().mapToInt(Integer::intValue)
                .sum();
        assertThat(sum).isEqualTo(0);
    }

}
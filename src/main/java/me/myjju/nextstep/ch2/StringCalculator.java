package me.myjju.nextstep.ch2;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final Pattern CUSTOM_STRING_PATTERN = Pattern.compile("^(?:\\/\\/(.)\\n)?(.+)");
    private static String BASIC_SEPARATOR = "\\,|\\:";

    public int add(String input) throws RuntimeException {

        Matcher matcher = CUSTOM_STRING_PATTERN.matcher(input);
        if (!matcher.find() || input.length() < 3) {
            return 0;
        }

        String separator = getSeparator(matcher);
        String data = matcher.group(2);


        int result = 0;
        try {
            Calculator calculator = Calculator.create(separator, data);
            result = calculator.operate();
        } catch (NumberFormatException e) { // 구분된 문자열이 숫자가 아닌경우

        }

        return result;
    }

    private String getSeparator(Matcher matcher) {
        String customSplitChar = matcher.group(1);
        if (customSplitChar == null) {
            return BASIC_SEPARATOR;
        }
        return BASIC_SEPARATOR + "|\\" + customSplitChar;
    }


    static class Calculator {
        private final List<Integer> lists;

        private Calculator(List<Integer> lists) {
            this.lists = lists;
        }

        public static Calculator create(String separator, String data) {
            String[] split = data.split(separator);
            List<Integer> lists = Arrays.stream(split).map(Calculator::parseUnsignedInt)
                    .collect(Collectors.toList());
            return new Calculator(lists);
        }

        private static Integer parseUnsignedInt(String value) {
            int i = Integer.parseInt(value);
            if (i < 0) {
                throw new RuntimeException("음수 예외");
            }
            return i;
        }

        public int operate() {
            return lists.stream().mapToInt(Integer::intValue).sum();
        }
    }


}

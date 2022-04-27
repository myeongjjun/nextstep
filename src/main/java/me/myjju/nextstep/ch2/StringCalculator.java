package me.myjju.nextstep.ch2;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {

    private static final Pattern CUSTOM_STRING_PATTERN = Pattern.compile("^(?:\\/\\/(.)\\n)?(.+)");
    private static final String BASIC_SEPARATOR = "\\,|\\:";

    public int add(String input) throws RuntimeException {
        // 입력값이 비어있으면
        if (isBlank(input)) {
            return 0;
        }

        // 입력값이 숫자로 변환이 되면
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
        }

        // 입력값 구분자, 합계 처리
        try {
            return operate(toIntegerList(input));
        } catch (NumberFormatException e) { // 구분된 문자열이 숫자가 아닌경우
        }

        return 0;
    }

    private boolean isBlank(String input) {
        return input == null || input.isBlank();
    }

    /**
     * 입력값을 바탕으로 구분자를 얻는 메서드
     *
     * @param matcher
     * @return 구분자
     */
    private String getSeparator(Matcher matcher) {
        String customSplitChar = matcher.group(1);
        if (customSplitChar == null) {
            return BASIC_SEPARATOR;
        }
        return BASIC_SEPARATOR + "|\\" + customSplitChar;
    }


스    private List<Integer> toIntegerList(String input) {
        Matcher matcher = CUSTOM_STRING_PATTERN.matcher(input);
        if (matcher.find()) {
            String separator = getSeparator(matcher);
            String data = matcher.group(2);
            String[] split = data.split(separator);
            return Arrays.stream(split)
                    .map(this::parseUnsignedInt)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Integer parseUnsignedInt(String value) {
        int i = Integer.parseInt(value);
        if (i < 0) {
            throw new RuntimeException("음수 예외");
        }
        return i;
    }

    private int operate(List<Integer> lists) {
        return lists.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}

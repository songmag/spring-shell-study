package com.mark.spring.shell;

import com.mark.spring.shell.exception.PatternMatcherException;
import com.mark.spring.shell.model.PatternMatcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcherTest {

    @Test
    void patternTest() throws PatternMatcherException {
        String testPattern = "test/../test";
        String result = patternResult("/[.]{2}", testPattern);
        PatternMatcher patternMatcher = new PatternMatcher("/[.]{2}");
        Assertions.assertThat(patternMatcher.test(testPattern)).isTrue();
        int start = patternMatcher.getStart();
        int end = patternMatcher.getEnd();
        Assertions.assertThat(testPattern.substring(start, end)).isEqualTo(result);
    }

    @Test
    @DisplayName("pattern 매칭시에 Group 으로 되지 않게 처리한다")
    void patternMatcherTest() {
        String testPattern = "test/../../../";
        PatternMatcher patternMatcher = new PatternMatcher("/([.]{2}|[.]{2}/)");
        Assertions.assertThatThrownBy(() -> patternMatcher.test(testPattern))
                .isExactlyInstanceOf(PatternMatcherException.class);
    }

    String patternResult(String regex, String value) {
        Matcher matcher = Pattern.compile(regex).matcher(value);
        boolean matchResult = matcher.find();
        if (!matchResult) {
            return "";
        }
        int groupCount = matcher.groupCount();
        if (groupCount == 0) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            StringBuilder builder = new StringBuilder(value);
            String result = builder.substring(startIndex, endIndex);
            return result;
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            result.add(matcher.group(i));
        }
        return result.toString();
    }
}

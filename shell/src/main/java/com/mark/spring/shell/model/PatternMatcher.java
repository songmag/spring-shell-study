package com.mark.spring.shell.model;

import com.mark.spring.shell.exception.PatternMatcherException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
    private int start;
    private int end;
    protected String lastFindString;
    protected Pattern pattern;

    public PatternMatcher(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public boolean test(String value) throws PatternMatcherException {
        Matcher matcher = pattern.matcher(value);
        boolean find = matcher.find();
        if(find) {
            this.lastFindString = value;
            useResult(matcher.toMatchResult());
        }
        return find;
    }

    public int getStart() {
        if (this.lastFindString == null) {
            throw new NullPointerException();
        }
        return this.start;
    }

    public int getEnd() {
        if (this.lastFindString == null) {
            throw new NullPointerException();
        }
        return this.end;
    }

    protected void useResult(MatchResult matchResult) throws PatternMatcherException {
        if (matchResult.groupCount() > 0) {
            throw PatternMatcherException.unSupportRegex();
        }
        this.start = matchResult.start();
        this.end = matchResult.end();
    }
}

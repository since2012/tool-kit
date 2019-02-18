package org.mark.fastjson.converter;

import org.apache.commons.lang3.StringUtils;
import org.mark.fastjson.exception.FastJsonException;
import org.mark.fastjson.util.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * 前端日期时间字符串转换类
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String dateString) {

        //若为空
        if (StringUtils.isBlank(dateString)) {
            return null;
        }

        String patternDate = "\\d{4}-\\d{1,2}-\\d{1,2}";
        String patternTimeMinutes = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}";
        String patternTimeSeconds = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";

        boolean dateFlag = Pattern.matches(patternDate, dateString);
        boolean timeMinutesFlag = Pattern.matches(patternTimeMinutes, dateString);
        boolean timeSecondsFlag = Pattern.matches(patternTimeSeconds, dateString);

        if (dateFlag) {
            return DateUtil.parseDate(dateString);
        } else if (timeMinutesFlag) {
            return DateUtil.parseTimeMinutes(dateString);
        } else if (timeSecondsFlag) {
            return DateUtil.parseTime(dateString);
        } else {
            throw new FastJsonException(400, "输入日期格式不对");
        }

    }
}
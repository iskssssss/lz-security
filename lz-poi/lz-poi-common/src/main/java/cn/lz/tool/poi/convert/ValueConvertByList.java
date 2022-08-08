package cn.lz.tool.poi.convert;

import cn.hutool.core.util.StrUtil;
import cn.lz.tool.poi.model.ReadCellInfo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/5 15:43
 */
public class ValueConvertByList implements ValueConvert<List<String>> {

    @Override
    public List<String> to(String title, ReadCellInfo readCellInfo, Object... otherParams) {
        Object value = readCellInfo.getValue();
        List<String> resultList = null;
        if (value instanceof Collection) {
            Collection<?> valueList = (Collection<?>) value;
            resultList = this.replaceChar(valueList);
        }
        if (value instanceof String) {
            String valueStr = (String) value;
            resultList = this.handlerString(valueStr);
        }
        if (resultList == null) {
            return null;
        }
        return resultList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void clear() {

    }

    /**
     *
     * @param valueStr
     * @return
     */
    private List<String> handlerString(String valueStr) {
        List<String> resultList = new LinkedList<>();
        int count = StrUtil.count(valueStr, "    ");
        if (count > 0) {
            valueStr = valueStr.replace(" ", "\n");
        }
        if (valueStr.contains("\n")) {
            List<String> collect = Arrays.stream(valueStr.split("\n")).collect(Collectors.toList());
            resultList.addAll(replaceChar(collect));
            return resultList;
        }
        int length = valueStr.length();
        StringBuilder sb = new StringBuilder();
        if (length > 2 && valueStr.contains(" ")) {
            for (int i = 0; i < length; i++) {
                char c = valueStr.charAt(i);
                if (c == ' ') {
                    if (sb.length() > 1) {
                        resultList.add(sb.toString());
                        sb = new StringBuilder();
                    }
                    continue;
                }
                sb.append(c);
            }
            resultList.add(sb.toString());
            return replaceChar(resultList);
        }
        resultList.add(valueStr.replaceAll(" ", "").replace("★", ""));
        return resultList;
    }

    private List<String> replaceChar(Collection<?> valueList) {
        List<String> resultListTemp = new ArrayList<>();
        for (Object value : valueList) {
            String valueStr = StrUtil.toString(value);
            if (StrUtil.isBlank(valueStr) || "null".equals(valueStr)) {
                continue;
            }
            List<String> handlerList = this.handlerString(valueStr);
            resultListTemp.addAll(handlerList);
        }
        int size = resultListTemp.size();
        if (size < 2) {
            return resultListTemp;
        }
        List<String> resultList = new ArrayList<>();
        for (int i = 0, step = 1; i < size; i = i + step, step = 1) {
            StringBuilder str = new StringBuilder();
            str.append(resultListTemp.get(i));
            boolean check = true;
            for (int j = i + 1; j < size; j++) {
                str.append(resultListTemp.get(j));
                step++;
                if (ValueConvertByList.FAULT_TOLERANCE_SET.contains(str.toString())) {
                    resultList.add(str.toString());
                    check = false;
                    break;
                }
            }
            if (check) {
                step = 1;
                resultList.add(resultListTemp.get(i));
            }
        }
        return resultList;
    }

    public static final Set<String> FAULT_TOLERANCE_SET = new HashSet<>();
    static {

    }
}

package cn.lz.poi.test.convert;

import cn.lz.tool.poi.convert.ValueConvert;
import cn.lz.tool.poi.model.ReadCellInfo;

import java.awt.*;

/**
 * TODO
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/8 15:21
 */
public class ValueHandlerHighlightType implements ValueConvert<Integer> {
    private static final Color greenTolerance = new Color(185, 255, 185);
    private static final Color redTolerance = new Color(255, 185, 185);
    private static final Color yellowTolerance = new Color(185, 185, 100);

    @Override
    public Integer to(String title, ReadCellInfo readCellInfo, Object... otherParams) {
        Object backgroundColor = readCellInfo.getBackgroundColor();
        if (backgroundColor instanceof String) {
            String backgroundColorStr = (String) backgroundColor;
            if (backgroundColorStr.startsWith("#")) {
                backgroundColorStr = backgroundColorStr.substring(1);
            }
            Color color = new Color(Integer.parseInt(backgroundColorStr, 16));
            if ("70AD47".equals(backgroundColorStr) || "339966".equals(backgroundColorStr) ||
                    "99CC00".equals(backgroundColorStr) || "008000".equals(backgroundColorStr) ||
                    this.areColorsWithinTolerance(Color.green, color, greenTolerance)) {
                // 绿灯
                return 3;
            } else if ("FFFF00".equals(backgroundColorStr) || this.areColorsWithinTolerance(Color.yellow, color, yellowTolerance)) {
                // 黄灯
                return 2;
            } else if ("FF0000".equals(backgroundColorStr) || this.areColorsWithinTolerance(Color.red, color, redTolerance)) {
                // 红灯
                return 1;
            }
            return null;
        }
        if (backgroundColor instanceof Short) {
            short backgroundColorShort = (Short) backgroundColor;
            if (backgroundColorShort == 57 || backgroundColorShort == 50 || backgroundColorShort == 17) {
                return 3;
            } else if (backgroundColorShort == 13) {
                return 2;
            } else if (backgroundColorShort == 10) {
                return 1;
            }
        }
        return null;
    }

    private boolean areColorsWithinTolerance(Color color1, Color color2, Color tolerance) {
        return (color1.getRed()   - color2.getRed()   < tolerance.getRed()   && color1.getRed()   - color2.getRed()   > -tolerance.getRed()  ) &&
                (color1.getBlue()  - color2.getBlue()  < tolerance.getBlue()  && color1.getBlue()  - color2.getBlue()  > -tolerance.getBlue() ) &&
                (color1.getGreen() - color2.getGreen() < tolerance.getGreen() && color1.getGreen() - color2.getGreen() > -tolerance.getGreen());
    }

    @Override
    public void clear() {

    }
}

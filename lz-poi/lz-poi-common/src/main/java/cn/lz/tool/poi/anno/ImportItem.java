package cn.lz.tool.poi.anno;

import cn.lz.tool.poi.convert.ValueConvert;
import cn.lz.tool.poi.convert.ValueConvertDefault;

import java.lang.annotation.*;

/**
 * 导入项信息
 *
 * @author 孔胜
 * @version 版权 Copyright(c)2022 LZJ
 * @date 2022/8/4 12:52
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ImportItem {

    /**
     * 标题
     *
     * @return 标题
     */
    String[] value();

    /**
     * 纵坐标
     *
     * @return 纵坐标
     */
    int xIndex();

    /**
     * 是否必须
     *
     * @return 是否必须
     */
    boolean required() default false;

    /**
     * 值转换器
     *
     * @return 值转换器
     */
    Class<? extends ValueConvert> typeHandler() default ValueConvertDefault.class;
}

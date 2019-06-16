package com.miner.disco.basic.assertion;

import com.miner.disco.basic.exception.BasicException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 支持验证参数的断言实用程序类
 *
 * @author: chencx
 **/
public abstract class Assert {

    /**
     * 断言一个布尔表达式
     *
     * @param expression 一个布尔表达式
     * @param code       如果断言失败，异常消息
     * @param message    如果断言失败，异常CODE
     * @param args       占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void state(boolean expression, Integer code, String message, Object... args) {
        if (!expression) {
            throw new BasicException(code, message, args);
        }
    }

    /**
     * 断言一个布尔表达式
     *
     * @param expression 一个布尔表达式
     * @param code       如果断言失败，则抛出异常CODE
     * @throws BasicException
     */
    public static void state(boolean expression, Integer code) {
        if (!expression) {
            throw new BasicException(code);
        }
    }


    /**
     * 断言一个布尔表达式， 抛出异常 {@code BaseException} 当 布尔表达式是 {@code false}.
     *
     * @param expression 一个布尔表达式
     * @param message    如果断言失败，异常消息
     * @param code       如果断言失败，异常CODE
     * @param args       占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void isTrue(boolean expression, Integer code, String message, Object... args) {
        if (!expression) {
            throw new BasicException(code, message, args);
        }
    }

    /**
     * 断言一个布尔表达式， 抛出异常 {@code BaseException} 当 布尔表达式是 {@code false}.
     *
     * @param expression 一个布尔表达式
     * @param code       如果断言失败，则抛出异常CODE
     * @throws BasicException
     */
    public static void isTrue(boolean expression, Integer code) {
        if (!expression) {
            throw new BasicException(code);
        }
    }


    /**
     * 断言一个对象是 {@code null}
     *
     * @param object  要检查的对象
     * @param code    如果断言失败，异常CODE
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void isNull(Object object, Integer code, String message, Object... args) {
        if (object != null) {
            throw new BasicException(code, message, args);
        }
    }

    /**
     * 断言一个对象是 {@code null}
     *
     * @param object 要检查的对象
     * @param code   如果断言失败，则抛出异常CODE
     * @throws BasicException
     */
    public static void isNull(Object object, Integer code) {
        if (object != null) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言对象不是 {@code null}.
     *
     * @param object  要检查的对象
     * @param code    如果断言失败，异常CODE
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void notNull(Object object, Integer code, String message, Object... args) {
        if (object == null) {
            throw new BasicException(code, message, args);
        }
    }

    public static void notNull(Object object, Integer code) {
        if (object == null) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言对象不是 {@code null}.
     *
     * @param object  要检查的对象
     * @param message 如果断言失败，则抛出异常Code
     */
    public static void notNull(Object object, BasicException message) {
        if (object == null) {
            throw new BasicException(message);
        }
    }


    /**
     * 断言给定的字符串不是空的; 也就是说，它不能是{@code null}，也不是空字符串。
     *
     * @param text    检查的字符串
     * @param code    如果断言失败，异常CODE
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException 如果文本是空的
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text, Integer code, String message, Object... args) {
        if (!StringUtils.hasLength(text)) {
            throw new BasicException(code, message, args);
        }
    }

    /**
     * 断言给定的字符串不是空的; 也就是说，它不能是{@code null}，也不是空字符串。
     *
     * @param text 检查的字符串
     * @param code 如果断言失败，则抛出异常Code
     * @throws BasicException 如果文本是空的
     * @see StringUtils#hasLength
     */
    public static void hasLength(String text, Integer code) {
        if (!StringUtils.hasLength(text)) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言给定的字符串包含有效的文本内容;也就是说，它不能是{@code null}，并且必须包含至少一个非空格字符。
     *
     * @param text    检查的字符串
     * @param code    如果断言失败，异常Code
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException 如果文本不包含有效的文本内容
     * @see StringUtils#hasText
     */
    public static void hasText(String text, Integer code, String message, Object... args) {
        if (!StringUtils.hasText(text)) {
            throw new BasicException(code, message, args);
        }
    }

    /**
     * 断言给定的字符串包含有效的文本内容;也就是说，它不能是{@code null}，并且必须包含至少一个非空格字符。
     *
     * @param text 检查的字符串
     * @param code 如果断言失败，则抛出异常Code
     * @throws BasicException 如果文本不包含有效的文本内容
     * @see StringUtils#hasText
     */
    public static void hasText(String text, Integer code) {
        if (!StringUtils.hasText(text)) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言给定的文本不包含给定的子字符串。
     *
     * @param textToSearch 待查找文本
     * @param substring    在文本中查找的子字符串
     * @param code         如果断言失败，异常Code
     * @param message      如果断言失败，异常消息
     * @param args         占位符参数, 在message消息上设置了占位符
     * @throws BasicException 如果文本包含子字符串
     */
    public static void doesNotContain(String textToSearch, String substring,
                                      Integer code,
                                      String message,
                                      Object... args) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new BasicException(code, message, args);
        }
    }


    /**
     * 断言给定的文本不包含给定的子字符串。
     *
     * @param textToSearch 待查找文本
     * @param substring    在文本中查找的子字符串
     * @param code         如果断言失败，则抛出异常code
     * @throws BasicException 如果文本包含子字符串
     */
    public static void doesNotContain(String textToSearch, String substring,
                                      Integer code) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言数组包含元素;也就是说，它不能是{@code null}，并且必须包含至少一个元素。
     *
     * @param array   待检查数组
     * @param code    如果断言失败，异常Code
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException 如果对象数组是 {@code null} 或不包含元素
     */
    public static void notEmpty(Object[] array, Integer code, String message, Object... args) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BasicException(code, message, args);
        }
    }

    /**
     * 断言数组包含元素;也就是说，它不能是{@code null}，并且必须包含至少一个元素。 <
     *
     * @param array 待检查数组
     * @param code  如果断言失败，则抛出异常Code
     * @throws BasicException 如果对象数组是 {@code null} 或不含元素
     */
    public static void notEmpty(Object[] array, Integer code) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言数组包含元素;也就是说，它不能是{@code null}，并且必须包含至少一个元素。 <
     *
     * @param array   待检查数组
     * @param message 如果断言失败，则抛出异常消息
     * @throws BasicException
     */
    public static void notEmpty(Object[] array, BasicException message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BasicException(message);
        }
    }

    /**
     * 断言数组不含为 {@code null} 的元素.
     *
     * @param array   待检查数组
     * @param code    如果断言失败，异常CODE
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void noNullElements(Object[] array, Integer code, String message, Object... args) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BasicException(code, message, args);
                }
            }
        }
    }

    /**
     * 断言数组不含为 {@code null} 的元素.
     *
     * @param array 待检查数组
     * @param code  如果断言失败，则抛出异常消息
     * @throws BasicException
     */
    public static void noNullElements(Object[] array, Integer code) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BasicException(code);
                }
            }
        }
    }

    /**
     * 断言集合包含元素; 也就是说，它不应该是{@code null}，并且必须包含至少一个元素。
     *
     * @param collection 待检查集合
     * @param code       如果断言失败，则抛出异常code
     * @param message    如果断言失败，则抛出异常消息
     * @param args       占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void notEmpty(Collection<?> collection, Integer code, String message, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BasicException(code, message, args);
        }
    }


    /**
     * 断言集合包含元素; 也就是说，它不应该是{@code null}，并且必须包含至少一个元素。
     *
     * @param collection 待检查集合
     * @param code       如果断言失败，则抛出异常code
     * @throws BasicException
     */
    public static void notEmpty(Collection<?> collection, Integer code) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BasicException(code);
        }
    }


    /**
     * 断言一个map 不是空的; 也就是说，它不应该是{@code null}，并且必须包含至少一个条目
     *
     * @param map     待检查map
     * @param code    如果断言失败，异常CODE
     * @param message 如果断言失败，异常消息
     * @param args    占位符参数, 在message消息上设置了占位符
     * @throws BasicException
     */
    public static void notEmpty(Map<?, ?> map, Integer code, String message, Object... args) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BasicException(code, message, args);
        }
    }


    /**
     * 断言一个map 不是空的; 也就是说，它不应该是{@code null}，并且必须包含至少一个条目
     *
     * @param map  待检查map
     * @param code 如果断言失败，则抛出异常CODE
     * @throws BasicException
     */
    public static void notEmpty(Map<?, ?> map, Integer code) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BasicException(code);
        }
    }

    /**
     * 断言一个map 不是空的; 也就是说，它不应该是{@code null}，并且必须包含至少一个条目
     *
     * @param map     待检查map
     * @param message 如果断言失败，则抛出异常消息
     * @throws BasicException
     */
    public static void notEmpty(Map<?, ?> map, BasicException message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BasicException(message);
        }
    }
}

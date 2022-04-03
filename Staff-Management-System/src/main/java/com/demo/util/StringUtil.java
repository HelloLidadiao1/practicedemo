package com.demo.util;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lihongjie
 * @date 2022/3/29
 */
public class StringUtil {
    private StringUtil(){

    }

    /**
     * 判断目标字符串是否为空
     * @param str 目标字符串
     * @return 若为null或者实际长度为0则返回true，否则返回false;
     */
    public static boolean isEmpty(String str){
        return str == null || (str.trim()).length() == 0;
    }

    /**
     * 判断集合是否为空
     * @param collection 目标字符串
     * @return 若为null或者实际长度为0则返回true，否则返回false;
     */
    public static boolean collectionIsNotEmpty(Collection collection){
        return Optional.ofNullable(collection).
                filter(temp->{return temp.size() > 0;}).isPresent();
    }
}

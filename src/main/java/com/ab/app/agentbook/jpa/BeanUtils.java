/*
* Copyright 2013-2020 Smartdot Technologies Co., Ltd. All rights reserved.
* SMARTDOT PROPRIETARY/CONFIDENTIAL. Use is subject to license
terms.
*
*/
package com.ab.app.agentbook.jpa;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.FatalBeanException;

/**
* <p>
* 
技术文档
版权所有 © 北京慧点科技有限公司 第 8 页 共 30 页
* @author <a href="mailto:liangzk@smartdot.com.cn">liangzk</a>
* @version 1.0, 2020年11月4日
*/
/**
 * @description Bean拷备、map转换工具类
 * @author 隐
 * @date 2018/11/8 17:55
 * @since JDK1.8
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
 
    /**
     * bean copy不复制null值
     * @author 隐
     * @date 2018-11-10
     * @param source
     * @param target
     */
    public static void copyPropertiesWithoutNull(Object source, Object target) {
        if(source == null || target == null){
            return;
        }
        Class<?> actualEditable = target.getClass();
        Class<?> sourceClass = source.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if(targetPd.getWriteMethod() == null) {
                continue;
            }
            PropertyDescriptor sourcePd = getPropertyDescriptor(sourceClass, targetPd.getName());
            if(sourcePd == null || sourcePd.getReadMethod() == null) {
                continue;
            }
            try {
                Method readMethod = sourcePd.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                Object value = readMethod.invoke(source);
                setValue(target, targetPd, value);
            } catch (Exception ex) {
                throw new FatalBeanException("Could not copy properties from source to target", ex);
            }
        }
    }
 
    /**
     * 设置值到目标bean
     * @param target
     * @param targetPd
     * @param value
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void setValue(Object target, PropertyDescriptor targetPd, Object value) throws IllegalAccessException, InvocationTargetException {
        // 这里判断以下value是否为空
        if (value != null) {
            Method writeMethod = targetPd.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            writeMethod.invoke(target, value);
        }
    }
}

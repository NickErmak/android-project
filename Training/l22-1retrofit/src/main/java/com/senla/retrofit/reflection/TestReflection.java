package com.senla.retrofit.reflection;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestReflection {

    public static void test() {
        try {
            Class<?> clazz = Class.forName("com.senla.retrofit.reflection.Tester");
            Tester tester = null;
            Constructor[] constructors = clazz.getConstructors();
            for (Constructor<Tester> constr : constructors) {
                tester = constr.newInstance("84");
            }

            clazz.getMethod("doPublic").invoke(tester);
            clazz.getMethod("doProtected").invoke(tester);
            Method methodPrivate = clazz.getDeclaredMethod("doPrivate");
            methodPrivate.setAccessible(true);
            methodPrivate.invoke(tester);

            for (Constructor<Tester> constr : constructors) {
                Log.e("TAG", "constructor param types:");
                Class[] paramTypes = constr.getParameterTypes();
                for (Class type : paramTypes) {
                    Log.e("TAG", "Param: " + type.getSimpleName());
                }
            }

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Log.e("TAG", "field = " + field.getName() + ", type = " + field.getType());
                if (field.isAnnotationPresent(TesterAttribute.class)) {
                    Log.e("TAG", "info: " + field.getAnnotation(TesterAttribute.class).info());
                }
            }

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Log.e("TAG", "method = " + method.getName());
                if (method.isAnnotationPresent(TesterMethod.class)) {
                    Log.e("TAG", "description: " + method.getAnnotation(TesterMethod.class).description());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

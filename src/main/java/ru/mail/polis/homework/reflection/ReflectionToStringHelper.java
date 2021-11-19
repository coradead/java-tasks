package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Необходимо реализовать метод reflectiveToString, который для произвольного объекта
 * возвращает его строковое описание в формате:
 * <p>
 * {field_1: value_1, field_2: value_2, ..., field_n: value_n}
 * <p>
 * где field_i - имя поля
 * value_i - его строковое представление (String.valueOf),
 * за исключением массивов, для которых value формируется как:
 * [element_1, element_2, ..., element_m]
 * где element_i - строковое представление элемента (String.valueOf)
 * элементы должны идти в том же порядке, что и в массиве.
 * <p>
 * Все null'ы следует представлять строкой "null".
 * <p>
 * Порядок полей
 * Сначала следует перечислить в алфавитном порядке поля, объявленные непосредственно в классе объекта,
 * потом в алфавитном порядке поля объявленные в родительском классе и так далее по иерархии наследования.
 * Примеры можно посмотреть в тестах.
 * <p>
 * Какие поля выводить
 * Необходимо включать только нестатические поля. Также нужно пропускать поля, помеченные аннотацией @SkipField
 * <p>
 * Упрощения
 * Чтобы не усложнять задание, предполагаем, что нет циклических ссылок, inner классов, и transient полей
 * <p>
 * Реализация
 * В пакете ru.mail.polis.homework.reflection можно редактировать только этот файл
 * или добавлять новые (не рекомендуется, т.к. решение вполне умещается тут в несколько методов).
 * Редактировать остальные файлы нельзя.
 * <p>
 * Баллы
 * В задании 3 уровня сложности, для каждого свой набор тестов:
 * Easy - простой класс, нет наследования, массивов, статических полей, аннотации SkipField (4 балла)
 * Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        Class<?> cls = object.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        while (cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class)) {
                    continue;
                }

                field.setAccessible(true);

                if (field.getType().isArray()) {
                    arrayFieldToStrBuilder(field, stringBuilder, object);
                } else {
                    fieldToStrBuilder(field, stringBuilder, object);
                }
                stringBuilder.append(", ");
            }
            cls = cls.getSuperclass();
        }
        // Deleting last ", " symbols, because they are unnecessary
        if (stringBuilder.length() >= 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }

    private static void arrayFieldToStrBuilder(Field field, StringBuilder stringBuilder, Object object) {
        try {
            stringBuilder.append(field.getName()).append(": ");
            if (field.get(object) == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append('[');

                Object arrayObject = field.get(object);
                for (int i = 0; i < Array.getLength(arrayObject); i++) {
                    Object arrayElementObject = Array.get(arrayObject, i);
                    stringBuilder.append(arrayElementObject).append(", ");
                }
                // Deleting last ", " symbols, because they are unnecessary
                if (Array.getLength(arrayObject) > 0) {
                    stringBuilder.setLength(stringBuilder.length() - 2);
                }

                stringBuilder.append(']');
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void fieldToStrBuilder(Field field, StringBuilder stringBuilder, Object object) {
        try {
            stringBuilder.append(field.getName()).append(": ");
            if (field.get(object) == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(field.get(object));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
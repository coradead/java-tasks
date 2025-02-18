package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    static final double ABS = 0.000000001d;
    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int A, int B, int C, int D) {
        double[] ans = new double[3];
        //New coefficients
        double a = B / (A * 1.0);
        double b = C / (A * 1.0);
        double c = D / (A * 1.0);
        //Smth like discriminant(the Vieta's theorem)
        double Q = (a * a - 3 * b) / 9;
        double R = (2 * a * a * a - 9 * a * b + 27 * c) / 54;
        double S = Q * Q * Q - R * R;
        if (S < 0) {
            throw new InputMismatchException("Wrong input");
        }
        //If s equal to zero
        if (Math.abs(S) < DoubleAdvancedTask.ABS) {
            ans[0] = -2 * Math.cbrt(R) - a / 3;
            ans[2] = ans[1] = Math.cbrt(R) - a / 3;
        } else {
            double Fi = Math.acos(R / Math.sqrt(Q * Q * Q)) / 3;
            ans[0] = -2 * Math.sqrt(Q) * Math.cos(Fi) - a / 3;
            ans[1] = -2 * Math.sqrt(Q) * Math.cos(Fi + 2 * Math.PI / 3) - a / 3;
            ans[2] = -2 * Math.sqrt(Q) * Math.cos(Fi - 2 * Math.PI / 3) - a / 3;
        }
        //Sorting
        //This is smth like swap without using extra memory(disregarding function call memory)
        //I thought we can't use arrays, sry
        Arrays.sort(ans);
        return ans[2] + ", " + ans[1] + ", " + ans[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 != a2) {
            return 0;
        }
        return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a1 + 1));
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1), (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости, что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
        //surface equation
        int[][] a = {
                {x4 - x1, y4 - y1, 0},
                {x2 - x1, y2 - y1, z2 - z1},
                {x3 - x1, y3 - y1, z3 - z1}
        };
        //express Z using matrix
        return (a[0][0] * a[1][2] * a[2][1] + a[0][1] * a[1][0] * a[2][2] - a[0][0] * a[1][1] * a[2][2] - a[0][1] * a[1][2] * a[2][0])
                / (a[1][0] * a[2][1] - a[1][1] * a[2][0] * 1.0) + z1;
    }
}

package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ������� ����������� � 2 �����.
 * ���� �� ����� ���������� �����.
 * ���� ��������� ������ ������� ����. ���� ����������� ����� positionPartString
 * ������� ������: ������� ��� ��������� ���������, �� ������� ����� ���������
 * ���������� �����. ��� �� �������� ��� �����, ������� �������� � ���������, ����������
 * �������� ������� ��� ������ � ���.
 * ������������ ����� ������ �� �(n).
 */
public class SearchInTheShredderList {
    private List<String> partStrings = new ArrayList<>();

    public SearchInTheShredderList() {
    }

    public SearchInTheShredderList(List<String> partStrings) {
        this.partStrings = partStrings;
    }

    public void add(String value) {
        partStrings.add(value);
    }

    public String get(int index) {
        return partStrings.get(index);
    }

    /**
     * ���� ������� �������� �� ������� ����� ��������� ������������ �����
     *
     * @param value - ������������� �����
     * @return - ���� ������ � ��������� ��������� �������� ���� �����, ���� - null
     */
    public int[] positionPartString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        int middle = value.length() / 2;
        String firstPart = value.substring(0, middle);
        String secondPart = value.substring(middle);
        int[] ans = new int[]{-1, -1};
        for (int i = 0; i < partStrings.size(); i++) {
            String s = partStrings.get(i);
            if (firstPart.equals(s)) {
                ans[0] = i;
            } else if (secondPart.equals(s)) {
                ans[1] = i;
            }
            if (ans[0] != -1 && ans[1] != -1) {
                break;
            }
        }
        if (ans[0] == -1 || ans[1] == -1) {
            return null;
        }
        return ans;
    }
}

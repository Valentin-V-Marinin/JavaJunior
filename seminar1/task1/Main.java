package seminar1.task1;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // генерируем список
        Random rnd = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) list.add(rnd.nextInt(-100, 101));
        System.out.println(list);

        // считаем среднее по четным числам списка
        int[] idx = new int[1]; idx[0] = 0;
        double[] sum = new double[1]; sum[0] = 0;
        list.stream().filter(n -> n % 2 == 0).forEach(n -> {
            sum[0] += n;
            idx[0]++;
        });
        System.out.println("sum:" + sum[0] + "  evenNumbers:" + idx[0] + "  AVR:" + (idx[0] == 0 ? 0 : sum[0] / idx[0]));

        // альтернативный расчет среднего
        System.out.println("Альтернативное среднее: " +
                list.stream()
                            .filter(n -> n%2 == 0)
                            .mapToInt(a -> a)
                            .average().orElse(0));

        // альтернатива №2
        System.out.println("Альтернативное среднее №2: " +
                list.stream()
                            .filter(n -> n%2 == 0)
                            .mapToInt(Integer::intValue)
                            .summaryStatistics()
                            .getAverage());

    }
}

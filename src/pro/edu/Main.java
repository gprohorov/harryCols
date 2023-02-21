package pro.edu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        LocalDateTime start = LocalDateTime.now();

        String text = new String(Files.readAllBytes(Paths.get("/home/george/Desktop/bigid.txt")));

        text = text.toLowerCase().replaceAll("[^A-Za-z ]","");
        String[] array = text.split(" +");
        List<String> distinct = Arrays.stream(array).distinct().collect(Collectors.toList());

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        ArrayList<Future<Map.Entry<String, Integer>>> futures = new ArrayList<>();

        Map<String, Integer> map = new HashMap<>();
       // Map<String, Long> collect = list.stream()
         //       .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (String word : distinct) {
            int[] counter = new int[1];
            Future<Map.Entry<String, Integer>> taskSubmitted =
            executorService.submit( () ->{
                counter[0] = 0;
                        for (int j = 0; j < array.length ; j++) {
                            if (word.equals(array[j])) counter[0]++;
                        }
                Map.Entry<String,Integer> entry =
                        new AbstractMap.SimpleEntry<String, Integer>(word, counter[0]); ;
                return entry;
                    });
            futures.add( taskSubmitted);
        }
        map = futures.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return new AbstractMap.SimpleEntry<String, Integer>("",0);
        }).collect(Collectors.toMap(el -> el.getKey(), el -> el.getValue()));

        executorService.shutdown();
        System.out.println(ChronoUnit.MILLIS.between(start,LocalDateTime.now())/1000.0  + " s");
        System.out.println(map.size());
        //    map.entrySet().stream().limit(20).forEach(System.out::println);
        Map<String, Integer> sorted = new LinkedHashMap<>();


        map.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .forEachOrdered(entry -> sorted.put(entry.getKey(), entry.getValue()));

        String sorted20 = "----------------------------" + "\n"
                + " HEAD for the task 1" + "\n"
                + "\n"
                + LocalDateTime.now().toString() + "\n"
                + "-----------------------------------" + "\n";
        int counter = 0;
        for (Map.Entry<String,Integer> entry : sorted.entrySet()){
            counter++;
            sorted20 += entry.getKey() + " " + entry.getValue() + "\n";
            if (counter == 20) break;
        }
        System.out.println(sorted20);
        System.out.println(ChronoUnit.MILLIS.between(start,LocalDateTime.now())/1000.0  + " s");
//        String sortedToString = sorted.toString();
//        Files.write(Paths.get("/home/george/Desktop/exam.txt"),
//                sorted20.getBytes(StandardCharsets.UTF_8));
//


    }
}

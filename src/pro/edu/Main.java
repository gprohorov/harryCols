package pro.edu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get("/home/george/Desktop/harry.txt")));

        text = text.toLowerCase().replaceAll("[^A-Za-z ]","");
        String[] array = text.split(" +");
        List<String> list = Arrays.stream(array).collect(Collectors.toList());

        Map<String, Integer> map = new HashMap<>();
        Integer value = 0;
        for (int i = 0; i < array.length; i++) {
            if(!map.containsKey(array[i])){
                map.put(array[i], 1);
            }else {
                 value = map.get(array[i]);
                 map.put(array[i], value + 1 );
            }
        }

    //    map.entrySet().stream().limit(20).forEach(System.out::println);
        Map<String, Integer> sorted = new LinkedHashMap<>();


        map.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .forEachOrdered(entry -> sorted.put(entry.getKey(), entry.getValue()));

        String sorted20 = "";
        int counter = 0;
        for (Map.Entry<String,Integer> entry : sorted.entrySet()){
            counter++;
            sorted20 += entry.getKey() + " " + entry.getValue() + "\n";
            if (counter == 20) break;
        }
        String sortedToString = sorted.toString();
        Files.write(Paths.get("/home/george/Desktop/exam.txt"),
                sorted20.getBytes(StandardCharsets.UTF_8));


    }
}

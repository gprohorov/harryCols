package pro.edu;

import java.io.IOException;
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

        map.entrySet().stream().limit(100).forEach(System.out::println);





    }
}

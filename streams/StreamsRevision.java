package com.example.multithreading.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsRevision {
    public static void main(String[] args) throws IOException {
        /* 1. Stream of Set */
        Set<String> st = Set.of("1","2");
        Stream<String> setStream =  st.stream();
      //  setStream.forEach(System.out::println);

        /* 2. Stream of List */
        List<String> list = List.of("1","2");
        Stream<String> listStream = list.stream();
  //      listStream.forEach(System.out::println);

        /* 3. Stream of Map */
        Map<String,String> mp = new HashMap<>();
        mp.put("key-1","value-1");
        mp.put("key-2","value-2");

        Stream<String> keyStream = mp.keySet().stream();
        Stream<String> valueStream = mp.values().stream();
        Stream<Map.Entry<String,String>> entryStream = mp.entrySet().stream();

        // Example Showing Use Case of Map Stream
        Stream<String> mapEntryEx = entryStream.map(entry -> entry.getKey() + "=" + entry.getValue());
      //  mapEntryEx.forEach(System.out::println);


        /* 4. Empty Stream */

        Stream<String> empty = Stream.empty();

//        /* 5. Generate Method For Unbounded Streams */
        Random random = new Random();
        Stream<Integer> integerUnboundedStream = Stream.generate(random::nextInt);
//        integerUnboundedStream.forEach(System.out::println);


        /* 6. Iterator */
        Stream<String> str = Stream.of("A","B","C");
        Iterator<String> iterator = str.iterator();


        /* 7. Ints Method */

        IntStream intStream = new Random().ints();
      //  intStream.forEach(System.out::println);
        IntStream charStream = "Avinash Soni".chars();
        // charStream.forEach(System.out::println);


        /* 8. Stream of strings using Regex */
        Stream<String> words = Pattern.compile(" ").splitAsStream("Hi My Name is Avinash Soni");
      //  words.forEach(System.out::println);

        /* 9.  Stream From a File */
        Path bookFile = Path.of("src/main/resources/abc.txt");
        Stream<String> bookStream = Files.lines(bookFile);
        bookStream.map(entry -> entry + "yooo" + "me").forEach(System.out::println);


        /* 10. Map Filter Reduce */
    }
}

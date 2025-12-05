package com.app.kanji.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class KanjiService {
    private List<KanjiReading> readings;

    private static final Pattern CSV_SPLIT = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

    public KanjiService() {
        loadCSV();
    }

    private void loadCSV() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kanji_readings.csv")),
                StandardCharsets.UTF_8))) {
            readings = reader.lines()
                    .skip(1)
                    .map(line -> CSV_SPLIT.split(line, -1)) // preserve empty fields
                    .map(fields -> Arrays.stream(fields)
                            .map(f -> f.replaceAll("^\"|\"$", "")) // remove surrounding quotes
                            .toArray(String[]::new))
                    .map(KanjiReading::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KanjiReading> findByMeaning(String meaning){
        String lower = meaning.toLowerCase();
        return readings.stream()
                .sorted((a, b) -> {
                    boolean aExact = isExactMatchMeaning(a, lower);
                    boolean bExact = isExactMatchMeaning(b, lower);
                    return Boolean.compare(!aExact, !bExact); // true=false < false=true
                })
                .filter(k -> isPartialMatchMeaning(k, lower))
                .collect(Collectors.toList());
    }

    public List<KanjiReading> findByReading(String reading) {
        String lower = reading.toLowerCase();

        return readings.stream()
                .sorted((a, b) -> {
                    boolean aExact = isExactMatchOnYomi(a, lower);
                    boolean bExact = isExactMatchOnYomi(b, lower);
                    return Boolean.compare(!aExact, !bExact); // true=false < false=true
                })
                .filter(k -> isPartialMatchOnYomi(k, lower))
                .collect(Collectors.toList());
    }

    private boolean isExactMatchMeaning(KanjiReading k, String input) {
        return k.getHauptbedeutung().equalsIgnoreCase(input)
                || k.getBedeutungOnYomi1().equalsIgnoreCase(input)
                || k.getBedeutungOnYomi2().equalsIgnoreCase(input)
                || k.getBedeutungKunYomi1().equalsIgnoreCase(input)
                || k.getBedeutungKunYomi2().equalsIgnoreCase(input);
    }

    private boolean isPartialMatchMeaning(KanjiReading k, String input) {
        return k.getHauptbedeutung().toLowerCase().contains(input)
                || k.getBedeutungOnYomi1().toLowerCase().contains(input)
                || k.getBedeutungOnYomi2().toLowerCase().contains(input)
                || k.getBedeutungKunYomi1().toLowerCase().contains(input)
                || k.getBedeutungKunYomi2().toLowerCase().contains(input);
    }

    private boolean isExactMatchOnYomi(KanjiReading k, String input) {
        return k.getOnYomi1().equalsIgnoreCase(input)
                || k.getOnYomi2().equalsIgnoreCase(input)
                || k.getKunYomi1().equalsIgnoreCase(input)
                || k.getKunYomi2().equalsIgnoreCase(input);
    }

    private boolean isPartialMatchOnYomi(KanjiReading k, String input) {
        return k.getOnYomi1().toLowerCase().startsWith(input)
                || k.getOnYomi2().toLowerCase().startsWith(input)
                || k.getKunYomi1().toLowerCase().startsWith(input)
                || k.getKunYomi2().toLowerCase().startsWith(input);
    }

    public List<KanjiReading> findAll() {
        return readings;
    }
}
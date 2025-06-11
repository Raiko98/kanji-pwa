package com.app.kanji.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.stereotype.Service;

@Service
public class KanjiService {
    private List<KanjiReading> readings;

    public KanjiService() {
        loadCSV();
    }

    private void loadCSV() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("kanji_readings.csv")),
                StandardCharsets.UTF_8))) {
            readings = reader.lines()
                    .skip(1)
                    .map(line -> line.split(",", -1))
                    .map(KanjiReading::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<KanjiReading> findByReading(String reading) {
        String lower = reading.toLowerCase();

        return readings.stream()
                .sorted((a, b) -> {
                    boolean aExact = isExactMatch(a, lower);
                    boolean bExact = isExactMatch(b, lower);
                    return Boolean.compare(!aExact, !bExact); // true=false < false=true
                })
                .filter(k -> isPartialMatch(k, lower))
                .collect(Collectors.toList());
    }

    private boolean isExactMatch(KanjiReading k, String input) {
        return k.getOnYomi1().equalsIgnoreCase(input)
                || k.getOnYomi2().equalsIgnoreCase(input)
                || k.getKunYomi1().equalsIgnoreCase(input)
                || k.getKunYomi2().equalsIgnoreCase(input);
    }

    private boolean isPartialMatch(KanjiReading k, String input) {
        return (k.getOnYomi1().toLowerCase().startsWith(input)
                || k.getOnYomi2().toLowerCase().startsWith(input)
                || k.getKunYomi1().toLowerCase().startsWith(input)
                || k.getKunYomi2().toLowerCase().startsWith(input));
    }

    public List<KanjiReading> findAll() {
        return readings;
    }
}
package com.app.kanji.view;

import com.app.kanji.*;

import com.app.kanji.domain.KanjiReading;
import com.app.kanji.domain.KanjiService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private void applyFilters(Grid<KanjiReading> grid,
                              KanjiService kanjiService,
                              TextField searchReading,
                              TextField searchMeaning) {

        String reading = searchReading.getValue().trim().toLowerCase();
        String meaning = searchMeaning.getValue().trim().toLowerCase();

        List<KanjiReading> results;

        // CASE 1 — both empty => clear grid
        if (reading.isEmpty() && meaning.isEmpty()) {
            results = List.of();
        }
        // CASE 2 — only reading entered
        else if (!reading.isEmpty() && meaning.isEmpty()) {
            results = kanjiService.findByReading(reading);
        }
        // CASE 3 — only meaning entered
        else if (reading.isEmpty() && !meaning.isEmpty()) {
            results = kanjiService.findByMeaning(meaning);
        }
        // CASE 4 — both entered => apply both filters
        else {
            List<KanjiReading> readingMatches = kanjiService.findByReading(reading);

            results = readingMatches.stream()
                    .filter(r -> r.getHauptbedeutung() != null
                            && r.getHauptbedeutung().toLowerCase().contains(meaning))
                    .toList();
        }

        grid.setItems(results);
    }

    public MainView(@Autowired KanjiService kanjiService) {
        TextField searchField = new TextField("Search On-/Kun-Yomi");
        TextField searchFieldMeaning = new TextField("Bedeutung");
        Grid<KanjiReading> grid = new Grid<>(KanjiReading.class, false);

        // Configure all columns manually for custom order
        grid.addColumn(KanjiReading::getKanji).setHeader("Kanji");
        grid.addColumn(KanjiReading::getHauptbedeutung).setHeader("Hauptbedeutung");
        grid.addColumn(KanjiReading::getOnYomi1).setHeader("on-yomi 1");
        grid.addColumn(KanjiReading::getBedeutungOnYomi1).setHeader("Bedeutung on-yomi 1");
        grid.addColumn(KanjiReading::getOnYomi2).setHeader("on-yomi 2");
        grid.addColumn(KanjiReading::getBedeutungOnYomi2).setHeader("Bedeutung on-yomi 2");
        grid.addColumn(KanjiReading::getKunYomi1).setHeader("kun-yomi 1");
        grid.addColumn(KanjiReading::getBedeutungKunYomi1).setHeader("Bedeutung kun-yomi 1");
        grid.addColumn(KanjiReading::getKunYomi2).setHeader("kun-yomi 2");
        grid.addColumn(KanjiReading::getBedeutungKunYomi2).setHeader("Bedeutung kun-yomi 2");
        grid.addColumn(KanjiReading::getKunYomi3).setHeader("kun-yomi 3");
        grid.addColumn(KanjiReading::getBedeutungKunYomi3).setHeader("Bedeutung kun-yomi 3");

        grid.getColumns().forEach(column -> {
            column.setAutoWidth(true);
            column.setFlexGrow(0); // Prevent squishing
            column.setResizable(true);
            column.setClassNameGenerator(item -> "wrap-text");
        });

        Div gridWrapper = new Div(grid);
        gridWrapper.setWidthFull();
        gridWrapper.getStyle().set("overflowX", "auto"); // horizontal scroll if needed


        searchField.addValueChangeListener(e ->
                applyFilters(grid, kanjiService, searchField, searchFieldMeaning)
        );


        searchFieldMeaning.addValueChangeListener(e ->
                applyFilters(grid, kanjiService, searchField, searchFieldMeaning)
        );

        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchFieldMeaning.setValueChangeMode(ValueChangeMode.EAGER);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();      // Let it use full space
        grid.setSizeFull();        // Let the grid expand inside
        layout.add(grid);

        setSizeFull();

        add(searchField,searchFieldMeaning, gridWrapper,layout);
    }
}
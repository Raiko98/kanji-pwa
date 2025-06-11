package com.app.kanji.domain;

public class KanjiReading {
    private String kanji;
    private String hauptbedeutung;
    private String onYomi1;
    private String bedeutungOnYomi1;
    private String onYomi2;
    private String bedeutungOnYomi2;
    private String kunYomi1;
    private String bedeutungKunYomi1;
    private String kunYomi2;
    private String bedeutungKunYomi2;

    public KanjiReading(String... data) {
        this.kanji = data[0];
        this.hauptbedeutung = data[1];
        this.onYomi1 = data[2];
        this.bedeutungOnYomi1 = data[3];
        this.onYomi2 = data[4];
        this.bedeutungOnYomi2 = data[5];
        this.kunYomi1 = data[6];
        this.bedeutungKunYomi1 = data[7];
        this.kunYomi2 = data[8];
        this.bedeutungKunYomi2 = data[9];
    }

    // Getters
    public String getKanji() { return kanji; }
    public String getHauptbedeutung() { return hauptbedeutung; }
    public String getOnYomi1() { return onYomi1; }
    public String getBedeutungOnYomi1() { return bedeutungOnYomi1; }
    public String getOnYomi2() { return onYomi2; }
    public String getBedeutungOnYomi2() { return bedeutungOnYomi2; }
    public String getKunYomi1() { return kunYomi1; }
    public String getBedeutungKunYomi1() { return bedeutungKunYomi1; }
    public String getKunYomi2() { return kunYomi2; }
    public String getBedeutungKunYomi2() { return bedeutungKunYomi2; }
}
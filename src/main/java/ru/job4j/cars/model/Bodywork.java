package ru.job4j.cars.model;

public enum Bodywork {
    SEDAN("Седан"),
    MINIVAN("Микроавтобус"),
    VAN("Фургон"),
    HATCHBACK("Хетчбэк"),
    PICKUP("Пикап"),
    CROSSOVER("Кроссовер");

    private final String name;

    Bodywork(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

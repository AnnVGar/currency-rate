package ru.ann.currencyrate.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Output {
    GRAPH("GRAPH"),
    LIST("LIST");

    private final String name;

}

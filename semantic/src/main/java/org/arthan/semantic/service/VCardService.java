package org.arthan.semantic.service;

import ezvcard.VCard;

import java.util.List;

/**
 * Created by Arthur Shamsiev on 25.04.15.
 * Using IntelliJ IDEA
 * Project - semantic
 */
public interface VCardService {
    List<VCard> findVCards();

    /**
     * Добавляет информацию из файла контактов в основной граф
     */
    void addToGraph();
}

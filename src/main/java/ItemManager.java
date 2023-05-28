package main.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;

public class ItemManager {
    private ObservableList<Item> items;
    private FilteredList<Item> filteredList;

    public ItemManager(List<Item> items) {
        this.items = FXCollections.observableArrayList(items);
        this.filteredList = new FilteredList<>(this.items);
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public FilteredList<Item> getFilteredList() {
        return filteredList;
    }

    public void applySearchFilter(String keyword) {
        filteredList.setPredicate(item ->
                item.getTitle().toLowerCase().contains(keyword) ||
                        item.getGenre().toLowerCase().contains(keyword));
    }
}
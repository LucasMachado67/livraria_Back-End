package com.projetolivraria.livraria.interfaces;

public interface BookDetailsDTO {
    Long getBookId();
    String getTitle();
    Integer getYear();
    Double getPrice();
    Integer getPages();
    String getLanguage();
    String getBookCover();
    byte[] getImage();
    Integer getQuantity();
    String getDescription();
    Long getAuthorId();
    String getAuthorName();
    String getCategories();
}

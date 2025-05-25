package com.projetolivraria.livraria.interfaces;

//DTO = Data Transfer Object, load the necessary data in different parts of your system (basically)
public interface BookDetailsDTO {
    Long getCode();
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

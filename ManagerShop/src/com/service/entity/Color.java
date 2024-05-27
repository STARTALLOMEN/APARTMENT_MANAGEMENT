/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service.entity;


public class Color {

    private int idColor;
    private String valueColor;

    public Color(int idColor, String valueColor) {
        this.idColor = idColor;
        this.valueColor = valueColor;
    }

    public Color() {
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getValueColor() {
        return valueColor;
    }

    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
    }

    @Override
    public String toString(){
        return valueColor;
    }
}

//Products FIle that defines each value function within a class to use for creating database
//Format followed from Professor Tsai's Code, does not properly run in conjunction with others
package com.deitel.finalproject.jruiz.finalproject;

public class Products {
    private double _amount;
    private double _conCurrency;
    public Products(double amount, double conCurrency) {

        this._conCurrency = conCurrency;
        this._amount= amount;
    }

    public double get_amount() {
        return _amount;
    }

    public void set_amount(double _amount) {
        this._amount = _amount;
    }

    public double get_conCurrency() {
        return _conCurrency;
    }

    public void set_conCurrency(double _conCurrency) {
        this._conCurrency = _conCurrency;
    }
}
package com.example.appbanco;

public class cuenta {
    private String usuario;
    private String Nrocuenta;
    private String saldo;

    public cuenta() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNrocuenta() {
        return Nrocuenta;
    }

    public void setNrocuenta(String nrocuenta) {
        Nrocuenta = nrocuenta;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}

package main;

import java.io.Serializable;

public class Flight implements Serializable {
    public int code;
    public String from;
    public String to;
    public Flight(int code, String from, String to){
        this.code = code;
        this.from = from;
        this.to = to;
    }
}

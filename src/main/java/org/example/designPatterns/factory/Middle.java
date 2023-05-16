package org.example.designPatterns.factory;

public class Middle {

    public Computer suggest(Integer type){
        if (type.equals(1)) {
            return new Alienware();
        } else {
            return new MacBook();
        }
    }
}

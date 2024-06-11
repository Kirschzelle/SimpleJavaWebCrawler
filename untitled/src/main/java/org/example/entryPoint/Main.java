package org.example.entryPoint;

public class Main {
    public static void main(String[] args) {
        Environment environment = new EnvironmentImpl();
        Application.run(environment);
    }
}


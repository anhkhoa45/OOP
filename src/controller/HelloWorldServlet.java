package controller;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", urlPatterns = {"hello"}, loadOnStartup = 1) // <1>
public class HelloWorldServlet extends javax.servlet.http.HttpServlet {
    private Gson _gson = new Gson();

    private class Car {
        private String name;
        private int id;

        private Car (int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Car myCar[] = {
                new Car(1, "Lambogini"),
                new Car(2, "Lambogini"),
                new Car(3, "Lambogini"),
                new Car(4, "Lambogini"),
        };

        response.setContentType("application/json");

        String res = _gson.toJson(myCar);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Car myCar = new Car(2, "Lambogini");

        response.setContentType("application/json");

        String res = _gson.toJson(myCar);

        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}

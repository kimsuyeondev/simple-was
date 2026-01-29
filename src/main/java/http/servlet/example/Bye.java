package http.servlet.example;

import http.server.core.HttpRequest;
import http.server.core.HttpResponse;
import http.servlet.SimpleServlet;

import java.io.IOException;
import java.io.Writer;

public class Bye implements SimpleServlet {

    @Override
      public void service(HttpRequest request, HttpResponse response) throws IOException {
        Writer writer = response.getWriter();
        writer.write("Bye! ");
        writer.write(request.getParameter("name"));
        writer.write("!");

      }
  }

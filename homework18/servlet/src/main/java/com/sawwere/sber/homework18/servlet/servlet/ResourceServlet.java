package com.sawwere.sber.homework18.servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(urlPatterns = "/recourse")
public class ResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String urlParam = req.getParameter("url");
        if (urlParam == null || urlParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Отсутствует необходимый параметр 'url'");
            return;
        }

        URL url = new URL(urlParam);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() == -1) {
            resp.sendError(HttpServletResponse.SC_BAD_GATEWAY);
            return;
        }

        try (var is = connection.getInputStream()) {
            is.transferTo(resp.getOutputStream());
        } catch (IOException ioException) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}

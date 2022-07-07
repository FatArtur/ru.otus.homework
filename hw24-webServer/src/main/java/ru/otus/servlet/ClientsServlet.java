package ru.otus.servlet;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.dao.ClientDao;
import ru.otus.services.TemplateProcessor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ClientsServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_CLIENTS = "allClients";
    private static final String CLIENT_NAME = "name";
    private static final String CLIENT_ADDRESS = "address";
    private static final String CLIENT_PHONE = "phone";

    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, ClientDao clientDao) {
        this.templateProcessor = templateProcessor;
        this.clientDao = clientDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_CLIENTS, clientDao.findAll().stream().map(Client::clientToDto).collect(Collectors.toList()));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(CLIENT_NAME);
        String address = request.getParameter(CLIENT_ADDRESS);
        String phone = request.getParameter(CLIENT_PHONE);

        var newClient = new Client(null, name, new Address(null, address), List.of(new Phone(null, phone)));
        clientDao.saveClient(newClient);
        response.sendRedirect("/clients");
    }


}

package org.geektimes.projects.user.web.controller;

import org.eclipse.microprofile.config.Config;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

/**
 * ConfigController
 *
 * @author qrXun on 2021/3/24
 */
@Path("/config")
public class ConfigController implements PageController {

    @Override
    @Path("")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        Config config = (Config) request.getServletContext().getAttribute("config");
        request.setAttribute("application.name", config.getValue("application.name", String.class));
        return "config-text.jsp";
    }
}

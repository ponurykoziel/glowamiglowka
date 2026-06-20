package com.gamebuilder.web.resource;

import com.gamebuilder.web.frontend.GameBuilderPages;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class GameBuilderPageResource {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public Response dashboard() {
        return Response.ok(GameBuilderPages.dashboard()).build();
    }
}
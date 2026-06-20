package com.gamebuilder.web.resource;

import com.gamebuilder.web.frontend.GameBuilderPages;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("")
public class GameBuilderPageResource {

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public Response dashboard() {
        return Response.ok(GameBuilderPages.dashboard()).build();
    }

    @GET
    @Path("/ui/palette")
    public Response paletteRedirect() {
        return Response.seeOther(URI.create("/")).build();
    }

    @GET
    @Path("/ui/artifacts")
    public Response artifactsRedirect() {
        return Response.seeOther(URI.create("/")).build();
    }

    @GET
    @Path("/ui/gamedef")
    public Response gameDefRedirect() {
        return Response.seeOther(URI.create("/")).build();
    }
}

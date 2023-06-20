package com.amiltone.rest;

import com.amiltone.domain.Author;
import com.amiltone.exceptions.ServiceException;
import com.amiltone.service.AuthorService;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.awt.*;
import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "author", description = "author Operations")
@AllArgsConstructor
@Slf4j
public class AuthorRessource {

    private final AuthorService authorService;

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All authors",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Author.class)
            )
    )
    public Response get(){
        return Response.ok(authorService.findAll()).build();
    }

    @GET
    @Path("/{authorId}")
    @APIResponse(
            responseCode = "200",
            description = "Get author by authorId",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Author.class)
            )
    )
    public Response getById(@Parameter(name = "authorId", required = true) @PathParam("authorId") UUID authorId){
        return authorService.findById(authorId)
                .map(author -> Response.ok(author).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "Author created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Author.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid author",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "author already exist for authorId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response post(@NotNull @Valid Author author, @Context UriInfo uriInfo) {
        authorService.save(author);
        URI uri = uriInfo.getAbsolutePathBuilder().path(author.getId().toString()).build();
        return Response.created(uri).entity(author).build();
    }

    @PUT
    @Path("/{authorId}")
    @APIResponse(
            responseCode = "204",
            description = "Author updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Author.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid author",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "author object does not have authorId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "No author found for authorId provided",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response put(@Parameter(name = "authorId", required = true) @PathParam("authorId") UUID authorId, @NotNull @Valid Author author) {
        if(Objects.equals(authorId, author.getId())) {
            throw new ServiceException("Path variable authorId does not match Author.authorId");
        }
        authorService.update(author);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

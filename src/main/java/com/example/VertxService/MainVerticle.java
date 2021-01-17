package com.example.VertxService;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.handler.CorsHandler;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start() throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(CorsHandler.create("http://localhost:3000"));
    router.get("/get").handler(context -> {
      // Get the address of the request
      String address = context.request().connection().remoteAddress().toString();
      // Get the query parameter "name"
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
      // Write a json response
      context.json(new JsonObject().put("name", name).put("address", address).put("message",
          "Hello " + name + " connected from " + address));
    });

    router.get("/getFromApi").blockingHandler(context -> {
      MultiMap headers = context.request().headers();
      String userId = headers.contains("authToken") ? headers.get("authToken") : "unknown";
      HttpRequest<JsonObject> request = WebClient.create(vertx)
          .getAbs("https://maceo.sth.kth.se/api/category/pmp3g/version/2/geotype/point/lon/14.333/lat/60.383/").
          as(BodyCodec.jsonObject());

      request.send(asyncResult -> {
        if (asyncResult.succeeded()) {
          String JsonArrayData = Logic.convertToJsonArray(asyncResult.result().body());
          Logic.saveToDB(userId, JsonArrayData);
          context.response().setStatusCode(200).end(JsonArrayData); 
        } else {
          context.response().setStatusCode(500).end(asyncResult.cause().getMessage());
        }
      });
    });

    router.get("/getFromDB").blockingHandler(context -> {
      MultiMap headers = context.request().headers();
      String userId = headers.contains("authToken") ? headers.get("authToken") : "unknown";
      String dataFromDB = Logic.getFromDB(userId);
      
      if (dataFromDB != null)
          context.response().setStatusCode(200).end(dataFromDB); 
      else  
          context.response().setStatusCode(500).end("Couldn't fetch the data from DB");
    });

    // Create the HTTP server
    vertx.createHttpServer().requestHandler(router).listen(8888)
        .onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
  }

}

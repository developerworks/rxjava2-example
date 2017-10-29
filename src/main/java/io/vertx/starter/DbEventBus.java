package io.vertx.starter;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.eventbus.EventBus;

public class DbEventBus extends AbstractVerticle {
  private static final String ADDRESS = "ping-address";

  @Override
  public void start() throws Exception {
    super.start();
    ///////////////////////
    // 反应式事件总线
    ///////////////////////
    EventBus eb = vertx.eventBus();

    eb.consumer(ADDRESS).toObservable().subscribe(message -> {
      System.out.println("Received " + message.body());
      message.reply("PONG");
    });

    // Send a message every second
    vertx.setPeriodic(1000, v -> {
      eb.rxSend(ADDRESS, "PING").subscribe(reply -> {
        System.out.println("Received reply " + reply.body());
      });
    });
  }
}

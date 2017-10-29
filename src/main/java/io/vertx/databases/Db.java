package io.vertx.databases;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.jdbc.JDBCClient;

public class Db extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();

    // JsonObject config = new JsonObject();
    // config = new JsonObject();
    // config.put("url", "jdbc:mysql://localhost:3306/testdb?useSSL=false");
    // config.put("provider_class", "io.vertx.ext.jdbc.spi.impl.C3P0DataSourceProvider");
    // config.put("driver_class", "com.mysql.jdbc.Driver");
    // config.put("user", "root");
    // config.put("password", "root");
    // config.put("max_pool_size", 15);
    // config.put("min_pool_size", 1);
    // config.put("max_statements", 100);
    // config.put("max_statements_per_connection", 10);
    // config.put("max_idle_time", 0);

    JsonObject config = new JsonObject();
    config.put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider");
    config.put("catalog", "testdb");
    // config.put("driverClassName", "com.mysql.jdbc.Driver");
    // config.put("connectionInitSql", "SET NAMES 'UTF8';");
    config.put("username", "root");
    config.put("password", "root");
    config.put("jdbcUrl", "jdbc:mysql://localhost:3306?useSSL=false");
    // config.put("autoCommit", true);
    // config.put("connectionTimeout", 1000);
    // config.put("connectionTestQuery", "SELECT 1");
    // config.put("minimumIdle", 20);
    // config.put("maximumPoolSize", 100);
    config.put("poolName", "hikaricp");
    // config.put("readOnly", false);
    JDBCClient jdbc = JDBCClient.createShared(vertx, config);
    jdbc
    .rxGetConnection() // Connect to the database
    .flatMapPublisher(conn -> { // With the connection...
      return conn
        .rxUpdate("CREATE TABLE IF NOT EXISTS test(col VARCHAR(20))") // ...create test table
        // .flatMap(result -> conn.rxUpdate("INSERT INTO test (col) VALUES ('val1')")) // ...insert a row
        // .flatMap(result -> conn.rxUpdate("INSERT INTO test (col) VALUES ('val2')")) // ...another one
        .flatMap(result -> conn.rxQueryStream("SELECT * FROM test")) // ...get values stream
        .flatMapPublisher(sqlRowStream -> {
          return sqlRowStream.toFlowable() // Transform the stream into a Flowable...
            .doOnTerminate(() -> {
              // ...and close the connection when the stream is fully read or an
              // error occurs
              conn.close();
              System.out.println("Connection closed");
            });
        });
    }).subscribe(row -> System.out.println("Row : " + row.encode()));
  }
}

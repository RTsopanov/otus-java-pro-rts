package rts.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import rts.NumbersServiceImpl;

import java.io.IOException;

@Slf4j
public class NumbersServer {

    public static final int PORT = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("numbers Server is starting...");

        NumbersServiceImpl numbersService = new NumbersServiceImpl();

        Server server = ServerBuilder.forPort(PORT)
                .addService(numbersService)
                .build();

        server.start();
        log.info("Server started, listening on port {}", PORT);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutting down server...");
            numbersService.shutdown();
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
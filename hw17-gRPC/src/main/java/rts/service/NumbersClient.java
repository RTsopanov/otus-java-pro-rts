package rts.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import rts.grpc.NumbersRequest;
import rts.grpc.NumbersServiceGrpc;
import rts.ClientStreamObserver;
import rts.ServerValueHolder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NumbersClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 50051;

    private static final long REQUEST_FROM = 0;
    private static final long REQUEST_TO = 30;

    private static final int LOOP_ITERATIONS = 50;
    private static final long TICK_PERIOD_SECONDS = 1;

    public static void main(String[] args) throws InterruptedException {
        log.info("numbers Client is starting...");

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        try {
            NumbersServiceGrpc.NumbersServiceStub asyncStub = NumbersServiceGrpc.newStub(channel);

            ServerValueHolder serverValueHolder = new ServerValueHolder();
            CountDownLatch finishLatch = new CountDownLatch(1);

            NumbersRequest request = NumbersRequest.newBuilder()
                    .setFirstValue(REQUEST_FROM)
                    .setLastValue(REQUEST_TO)
                    .build();

            asyncStub.getNumbers(request, new ClientStreamObserver(serverValueHolder, finishLatch));

            long currentValue = 0;
            for (int i = 0; i < LOOP_ITERATIONS; i++) {
                TimeUnit.SECONDS.sleep(TICK_PERIOD_SECONDS);

                long fromServer = serverValueHolder.consume();
                currentValue = currentValue + fromServer + 1;

                log.info("currentValue:{}", currentValue);
            }

            finishLatch.await(5, TimeUnit.SECONDS);

        } finally {
            channel.shutdown();
            try {
                channel.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
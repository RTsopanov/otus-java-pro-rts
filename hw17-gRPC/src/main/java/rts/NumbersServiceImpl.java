package rts;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import rts.grpc.NumberResponse;
import rts.grpc.NumbersRequest;
import rts.grpc.NumbersServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NumbersServiceImpl extends NumbersServiceGrpc.NumbersServiceImplBase {

    private static final long PERIOD_SECONDS = 2;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    @Override
    public void getNumbers(NumbersRequest request, StreamObserver<NumberResponse> responseObserver) {
        long firstValue = request.getFirstValue();
        long lastValue = request.getLastValue();

        log.info("получен запрос: firstValue={}, lastValue={}", firstValue, lastValue);

        ServerCallStreamObserver<NumberResponse> serverCallStreamObserver =
                (ServerCallStreamObserver<NumberResponse>) responseObserver;

        final long[] currentValue = {firstValue};
        ScheduledFuture<?>[] futureHolder = new ScheduledFuture<?>[1];

        Runnable task = () -> {
            try {
                currentValue[0] += 1;
                if (currentValue[0] > lastValue) {
                    responseObserver.onCompleted();
                    futureHolder[0].cancel(false);
                    return;
                }
                NumberResponse response = NumberResponse.newBuilder()
                        .setValue(currentValue[0])
                        .build();
                log.info("отправляю значение: {}", currentValue[0]);
                responseObserver.onNext(response);
            } catch (Exception e) {
                log.error("Ошибка при отправке значения клиенту", e);
                futureHolder[0].cancel(false);
            }
        };

        futureHolder[0] = scheduler.scheduleAtFixedRate(task, PERIOD_SECONDS, PERIOD_SECONDS, TimeUnit.SECONDS);

        serverCallStreamObserver.setOnCancelHandler(() -> {
            log.info("клиент отменил запрос, останавливаю стрим");
            futureHolder[0].cancel(false);
        });
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
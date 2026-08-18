package rts;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import rts.grpc.NumberResponse;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ClientStreamObserver implements StreamObserver<NumberResponse> {
    private final ServerValueHolder serverValueHolder;
    private final CountDownLatch finishLatch;

    public ClientStreamObserver(ServerValueHolder serverValueHolder, CountDownLatch finishLatch) {
        this.serverValueHolder = serverValueHolder;
        this.finishLatch = finishLatch;
    }

    @Override
    public void onNext(NumberResponse numberResponse) {
        long value = numberResponse.getValue();
        log.info("new value:{}", value);
        serverValueHolder.update(value);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Ошибка при получении данных от сервера", throwable);
        finishLatch.countDown();
    }

    @Override
    public void onCompleted() {
        log.info("request completed");
        finishLatch.countDown();
    }
}
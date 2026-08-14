package rts;

import rts.service.ServiceRunSemaphore;

public class Main {
    public static void main(String[] args) {
        ServiceRunSemaphore serviceRunSemaphore = new ServiceRunSemaphore();

        serviceRunSemaphore.myRun(10);
    }
}
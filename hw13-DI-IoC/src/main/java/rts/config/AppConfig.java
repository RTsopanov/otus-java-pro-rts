package rts.config;


import rts.appcontainer.api.AppComponent;
import rts.appcontainer.api.AppComponentsContainerConfig;
import rts.services.EquationPreparer;
import rts.services.EquationPreparerImpl;
import rts.services.GameProcessor;
import rts.services.GameProcessorImpl;
import rts.services.IOService;
import rts.services.IOServiceStreams;
import rts.services.PlayerService;
import rts.services.PlayerServiceImpl;

@AppComponentsContainerConfig(order = 1)
public class AppConfig {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer() {
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(
            IOService ioService, PlayerService playerService, EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @SuppressWarnings("squid:S106")
    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
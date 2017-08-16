package app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import config.GameResourceInjector;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import org.apache.log4j.Logger;
import resources.GameStoreResource;

public class DropWizard extends Application<Configuration>{

    private final static Logger logger = Logger.getLogger(DropWizard.class);

    public static void main(String[] args) throws Exception {
        new DropWizard().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        logger.info("DropWizard starting");

        Injector injector = Guice.createInjector(new GameResourceInjector());
        GameStoreResource gameStoreResource = injector.getInstance(GameStoreResource.class);

        environment.jersey().register(gameStoreResource);
    }
}

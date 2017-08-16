package config;

import com.google.inject.AbstractModule;
import database.GameStoreService;
import database.GameStoreServiceJDBI;

public class GameResourceInjector extends AbstractModule{

    @Override
    protected void configure() {
        bind(GameStoreService.class).to(GameStoreServiceJDBI.class);
    }
}

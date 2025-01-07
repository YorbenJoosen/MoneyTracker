package app.config;

public class Config {
    private static Config instance;

    private final DatabaseTypeEnum databaseType;

    private Config() {
        // Todo getenv
        databaseType = DatabaseTypeEnum.inmemory;
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public DatabaseTypeEnum getDatabaseType() {
        return databaseType;
    }
}

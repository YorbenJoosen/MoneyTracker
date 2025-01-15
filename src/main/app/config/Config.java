package app.config;

import app.tally.FirstFitStrategy;
import app.tally.TallyStrategy;

public class Config {
    private static volatile Config instance;

    private final DatabaseTypeEnum databaseType;
    private TallyStrategy tallyStrategy;
    private Config() {
        // Todo getenv
        databaseType = DatabaseTypeEnum.inmemory;
        tallyStrategy = new FirstFitStrategy();
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public DatabaseTypeEnum getDatabaseType() {
        return databaseType;
    }

    public TallyStrategy getTallyStrategy() {
        return tallyStrategy;
    }

    public void setTallyStrategy(TallyStrategy tallyStrategy) {
        this.tallyStrategy = tallyStrategy;
    }
}

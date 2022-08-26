package org.amos.core.generator.gen.postgresql;

import org.amos.core.generator.gen.SQLService;
import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.TableSelector;

/**
 * @author tanghc
 */
public class PostgreSqlService implements SQLService {
    @Override
    public TableSelector getTableSelector(GenConfig generatorConfig) {
        return new PostgreSqlTableSelector(new PostgreSqlColumnSelector(generatorConfig), generatorConfig);
    }

}

package org.amos.core.generator.gen.oracle;

import org.amos.core.generator.gen.SQLService;
import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.TableSelector;

public class OracleService implements SQLService {

	@Override
	public TableSelector getTableSelector(GenConfig generatorConfig) {
		return new OracleTableSelector(new OracleColumnSelector(generatorConfig), generatorConfig);
	}

}

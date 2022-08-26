package org.amos.core.generator.gen.mysql;

import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.SQLService;
import org.amos.core.generator.gen.TableSelector;

public class MySqlService implements SQLService {

	@Override
	public TableSelector getTableSelector(GenConfig generatorConfig) {
		return new MySqlTableSelector(new MySqlColumnSelector(generatorConfig), generatorConfig);
	}

}

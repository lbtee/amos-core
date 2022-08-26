package org.amos.core.generator.gen.sqlserver;

import org.amos.core.generator.gen.SQLService;
import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.TableSelector;

public class SqlServerService implements SQLService {

	@Override
	public TableSelector getTableSelector(GenConfig generatorConfig) {
		return new SqlServerTableSelector(new SqlServerColumnSelector(generatorConfig), generatorConfig);
	}

}

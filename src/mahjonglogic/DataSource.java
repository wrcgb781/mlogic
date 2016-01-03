package mahjonglogic;
/**
 * 参照
 * http://svn.apache.org/viewvc/commons/proper/dbcp/trunk/doc/BasicDataSourceExample.java?revision=1660584&view=markup
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DataSource {

	private BasicDataSource bds = new BasicDataSource();

	private DataSource() {
		Properties properties = new Properties();
		try {
			//dbcp.propertiesを読み込む
			InputStream is = ClassLoader.getSystemResourceAsStream("mahjonglogic/dbcp.properties");
			properties.load(is);

			//url文字列の作成と追加
			Config c = new Config();
			c.getFileMahjonglogicConfig();
			properties.put("url","jdbc:sqlite:/" + c.getMahjonglogicConfig().get("databasePath"));

			
			this.bds = BasicDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class DataSourceHolder {
		private static final DataSource INSTANCE = new DataSource();
	}

	public static DataSource getInstance() {
		return DataSourceHolder.INSTANCE;
	}

	public BasicDataSource getBds() {
		return bds;
	}

	public void setBds(BasicDataSource bds) {
		this.bds = bds;
	}
}
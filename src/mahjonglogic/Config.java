/**
 *
 */
package mahjonglogic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;




public class Config {

	private HashMap<String,String> mahjonglogicConfig; //設定値配列
	//改行コード
	private static final String BR = System.getProperty("line.separator");

	
	
	public HashMap<String, String> getMahjonglogicConfig() {
		return mahjonglogicConfig;
	}
	public void setMahjonglogicConfig(HashMap<String, String> mahjonglogicConfig) {
		this.mahjonglogicConfig = mahjonglogicConfig;
	}

	public void getFileMahjonglogicConfig(){
		mahjonglogicConfig = new HashMap<String,String>();

		Properties properties = new Properties();
		InputStream is = ClassLoader.getSystemResourceAsStream("mahjonglogic/mahjonglogic.properties");
		try {
			properties.load(is);
			mahjonglogicConfig.put("databasePath",properties.getProperty("databasePath"));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void setFileMahjonglogicConfig(){
			URL url = getClass().getClassLoader().getResource("mahjonglogic/mahjonglogic.properties");
			URI uri;
			try {
				uri = url.toURI();
				File confFile = new File(uri);
				FileWriter filewriter = new FileWriter(confFile);
				
				//mahjonglogicConfig配列を書き出す
				//コンフィグ連想配列のIteratorインスタンスの作成
				Iterator it = mahjonglogicConfig.keySet().iterator();
				while (it.hasNext()) {
				    Object o = it.next();
				    filewriter.write(o + "=" + mahjonglogicConfig.get(o) + BR);
				}
				filewriter.close();	
				
			
			} catch (URISyntaxException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}

		
	}	
}

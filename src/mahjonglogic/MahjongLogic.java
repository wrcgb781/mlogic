package mahjonglogic;

public class MahjongLogic {

	protected String loginId = null;
	protected String loginPassword = null;
	protected String drmlUrl = null;
	protected String saveLoginId = null;

	
	MahjongLogic(){
//		getProperties();
	
	}
	
//	protected void getProperties() {
//		Properties properties = new Properties();
//		try {
//			//MahjonglogicDataAnalysisを読み込む
//			InputStream is = ClassLoader.getSystemResourceAsStream("mahjonglogic/mahjonglogic.properties");
//			properties.load(is);
//			loginId = properties.getProperty("loginId");
//			loginPassword = properties.getProperty("loginPassword");
//			drmlUrl = properties.getProperty("drmlUrl");
//				saveLoginId = properties.getProperty("saveLoginId");
//
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}

		String[] loginPram = {loginId,loginPassword,drmlUrl,saveLoginId};

		//		UILoginInfo uil = new UILoginInfo();

//		uil.main(loginPram);
	
//	}

	
}

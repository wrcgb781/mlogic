package mahjonglogic;

public class ReportControllerFactory {

	ReportControllerFactory(){
		
	}

	public AbstractReportController getInstance(int type) {
		AbstractReportController rc;

		switch(type){
		case 0:
			rc =new DayReportController();
			break;
		case 1:
			rc =new WeekdayReportController();
			break;
		case 2:
			rc =new HourReportController();
			break;
		case 3:
			rc =new UserReportController();
			break;

		default:
			rc = null;
			break;
		}


		return (rc);
	}



}

package start;

import javax.swing.JProgressBar;

import mvc.common.util.DataObservable;

public class AppProgressBar {

	private static AppProgressBar instance = null;
	private static  DataObservable<JProgressBar> progressBarValue = null;


	public static AppProgressBar getInstance() {
		if(instance == null){
			instance = new AppProgressBar();
		}
		return instance;
	}
	
	public void setProgressBarValue(DataObservable<JProgressBar> progressBarValue2) {
		progressBarValue = progressBarValue2;
	}
	

	public void setMaximumProgressBar(int max){
		JProgressBar jBar =progressBarValue.getValue();
		jBar.setMaximum(max);
		progressBarValue.setValue(jBar);
	}

	public void setOneMoreProgressBar(){
		JProgressBar jBar =progressBarValue.getValue();
		jBar.setValue(jBar.getValue()+1);
		if(jBar.getValue()>=jBar.getMaximum())
			jBar.setValue(0);
		progressBarValue.setValue(jBar);
	}
	
}

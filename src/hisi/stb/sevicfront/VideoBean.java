package hisi.stb.sevicfront;

/**
 * 每个设置条目的设置值的bean。
 * @author richardzhou
 *
 */
public class VideoBean {

	
	public String getDisModle() {
		return disModle;
	}
	public void setDisModle(String disModle) {
		this.disModle = disModle;
	}
	public String getBrightNess() {
		return brightNess;
	}
	public void setBrightNess(String brightNess) {
		this.brightNess = brightNess;
	}
	public String getColorTemp() {
		return colorTemp;
	}
	public void setColorTemp(String colorTemp) {
		this.colorTemp = colorTemp;
	}
	public String getDynamicCon() {
		return dynamicCon;
	}
	public void setDynamicCon(String dynamicCon) {
		this.dynamicCon = dynamicCon;
	}
	public String getIntellColor() {
		return intellColor;
	}
	public void setIntellColor(String intellColor) {
		this.intellColor = intellColor;
	}
	public String getSharpen() {
		return sharpen;
	}
	public void setSharpen(String sharpen) {
		this.sharpen = sharpen;
	}
	public String getDenoise() {
		return denoise;
	}
	public void setDenoise(String denoise) {
		this.denoise = denoise;
	}
	public String getReset() {
		return reset;
	}
	public void setReset(String reset) {
		this.reset = reset;
	}
	
	private String disModle;
	private String brightNess;
	private String colorTemp;
	private String dynamicCon;
	private String intellColor;
	private String sharpen;
	private String denoise;
	private String reset;
	
}

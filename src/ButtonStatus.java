class Button{
	int index;
	int midiIndex;
	int color;
	int prevColor;
	int action; //0:Off,1:On,2:Blink,3:Faide,4:
	int ActCounter; 
	int LastUpdatedBy;
	boolean wroteNote; //false if the note is not yet on the launchpad
	Button(){;}
	
}

class ButtonList{
	 
	ButtonList(int[] newmidiIndex){;}
	
}

//need an array of color intensity
public class ButtonStatus{
	final int[] midiIndex = { 12, 13, 14, 15, 16, 17, 18, 19, 200, 24, 25, 26, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 42, 43, 44, 48, 49, 50, 51, 52, 53, 54, 55, 56, 60, 61, 62, 63, 64, 65, 66, 67, 68, 72, 73, 74, 75, 76, 77, 78, 79, 80, 84, 85, 86, 87, 88, 89, 90, 91, 92, 96, 97, 98, 99, 100, 101, 102, 103, 104, 108, 109, 110, 111, 112, 113, 114, 115, 116 };
final int initColor = 0;
//current color, who changed it last? 
	ButtonStatus(){
	
	//update, refresh, init/clear, change, check, getCurrent 
	
	}//end construtor
} //end class



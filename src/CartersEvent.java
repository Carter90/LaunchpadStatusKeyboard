import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
public class CartersEvent {
		static int[] butPos = { 12, 13, 14, 15, 16, 17, 18, 19, 200, 24, 25, 26, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 42, 43, 44, 48, 49, 50, 51, 52, 53, 54, 55, 56, 60, 61, 62, 63, 64, 65, 66, 67, 68, 72, 73, 74, 75, 76, 77, 78, 79, 80, 84, 85, 86, 87, 88, 89, 90, 91, 92, 96, 97, 98, 99, 100, 101, 102, 103, 104, 108, 109, 110, 111, 112, 113, 114, 115, 116 };
		static final String jeffAmen = "Our Jeff who art in Python, hallowed be thy name. Thy sarcasm come, Thy loop be done, in script as it is in REPL. Give us this day our daily sass, and forgive those who use indexes to loop over sequences as we forgive those who mix spaces with tabs. And lead us not into fault segmentation, but deliver us from errors, Amen.";
		CartersEvent(int button, Note note) throws Exception{
			note.note(button, 62);
			note.note(button, 0);
			/*
			Red 3
			Green 60 
			Yellow 62
			Ornge  63
			*/

			//Top bar
			if (button == 12) {
				note.initDisplay(0);
				//System.err.println("Maze:"+new p8(new MazeGenerator(9,"").getMA()).getpath());
				note.readAndDisplaymazePath(new p8(new MazeGenerator(9,"").getMA()).getpath());
			}
			else if (button == 13){note.coolColors();}
				//xdotool key F35 or xdotool type Hello
			else if (button == 14){execCmd("xdotool key F35"); }
			else if (button == 15){execCmd("xdotool type Hello"); }
			else if (button == 16){execCmd("xdotool type World"); }
			else if (button == 17){Runtime.getRuntime().exec(new String[]{"espeak","i,am,sorry,dave,i,cant,do,that"}); }
			else if (button == 30){Runtime.getRuntime().exec(new String[]{"espeak",jeffAmen}); }			

			//change into constant running processes
			else if (button == 19){ LoadAverage(note);	 } //##################### Load Average on top row #####################
			else if (button == 18){ BatteryCharge(note); }
			
			else if (button == 24){
				note.note(button, 63);
				execCmd("bash /home/carter/bin/pickup"); 
				note.note(button, 0);}
			else if (button == 36){
				note.note(button, 63);
				execCmd("bash /home/carter/bin/putdown"); 
				note.note(button, 0);}
			
			//Middle
			else if (button == 64){execCmd("xdotool key Up"); }
			else if (button == 65){execCmd("xdotool key Return"); }
			else if (button == 76){execCmd("xdotool key Down"); }
			else if (button == 75){execCmd("xdotool key Left"); }
			else if (button == 77){execCmd("xdotool key Right"); }

			//Right Side 
			else if (button == 31){execCmd("xdotool key Page_Up"); }
			else if (button == 32){execCmd("xdotool key Shift+Page_Up"); }
			else if (button == 43){execCmd("xdotool key Page_Down"); }
			else if (button == 44){execCmd("xdotool key Shift+Page_Down"); }
			else if (button == 68) {note.initDisplay(60);} //Green
			else if (button == 80) {note.initDisplay(63);} //Ornge
			else if (button == 92) {note.initDisplay(62);} //yellow
			else if (button == 104) {note.initDisplay(3);} //red
			else if (button == 116) {note.initDisplay(0);} //Blank
			
			else if (button == 108) {System.exit(1);} 
			//wget -P /tmp/npr npr.org/rss/podcast.php?id=510318
			//wget -P /tmp/npr `cat /tmp/podcast.php?id=510318 | grep "<enclosure url=" | head -n1 | cut -d";" -f1 | cut -d"\"" -f2`
			//mpg123 /tmp/npr/*upfirst_up_first_*.mp3*
			//upfirst_up_first_*.mp3*
		} //end CartersEvent

	//Thanks to 735Tesla https://stackoverflow.com/users/2517106/735tesla
	public static String execCmd(String cmd) throws java.io.IOException { 
		    java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
		    return s.hasNext() ? s.next() : "";
    }

	public static void LoadAverage(Note note)throws Exception{
				//##################### Load Average on top row #####################
				//int[] loadbar = {12,13,14,15,16,17,18,19};
				Scanner in = new Scanner( new BufferedReader(new FileReader(new File("/proc/loadavg"))));
				double load = in.nextDouble() * 2.0;
				//note.initDisplayRow(0,0);
				for (int i=0; ((i <= load) && (i < 8)); i++){ note.note(butPos[i], 63); }
		
	} //end LoadAverage

	public static void BatteryCharge(Note note)throws Exception{
		//##################### Battery Charge on top row #####################
		//int[] loadbar = {12,13,14,15,16,17,18,19};
		int[] BatBar = {32,44,56,68,80,92,104,116};
		Scanner in = new Scanner( new BufferedReader(new FileReader(new File("/sys/class/power_supply/BAT0/capacity"))));
		double batterycharge = in.nextDouble()/100*8;
		//note.initDisplayCol(0,8);
		for (int i=0; ((i <= batterycharge) && (i < 8)); i++){ note.note(BatBar[i], 62); }
					//expr $(upower -i /org/freedesktop/UPower/devices/battery_BAT1 | grep -E "percentage" | tr -d '[:space:]' | cut -d':' -f2 | cut -d'%' -f1) / 12
	}//end BatteryCharge
} //end CartersEvent class

//V0.01 Working 9/8/17 12:17am
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Note {
	private static boolean DEBUG = false;
	static int[] butPos = { 12, 13, 14, 15, 16, 17, 18, 19, 200, 24, 25, 26, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 42, 43, 44, 48, 49, 50, 51, 52, 53, 54, 55, 56, 60, 61, 62, 63, 64, 65, 66, 67, 68, 72, 73, 74, 75, 76, 77, 78, 79, 80, 84, 85, 86, 87, 88, 89, 90, 91, 92, 96, 97, 98, 99, 100, 101, 102, 103, 104, 108, 109, 110, 111, 112, 113, 114, 115, 116 };


/*
	Red 3
	Green 60 
	Yellow 62
	Ornge 63
*/
	public Note(){
		; //initDisplay(0);
	}

	public static void initDisplay(int color){
		for (int i = 0; i < 8; i++){ note(butPos[i], color); }
		for (int i = 17; i > 7; i--){ note(butPos[i], color); }
		for (int i = 18; i < 27 ; i++){ note(butPos[i], color); }
		for (int i = 35; i > 26; i--){ note(butPos[i], color); }
		for (int i = 36; i < butPos.length ; i++){ note(butPos[i], color); }
	}

	public void initDisplayRow(int color, int row){
		if(row == 0) { for (int i = 0; i < 8; i++){ note(butPos[i], color); } } 
		if(row == 1) { for (int i = 17; i > 7; i--){ note(butPos[i], color); } } 
		if(row == 2) { for (int i = 18; i < 27 ; i++){ note(butPos[i], color); } } 
		if(row == 3) { for (int i = 35; i > 26; i--){ note(butPos[i], color); } } 
		if(row == 4) { for (int i = 36; i < butPos.length ; i++){ note(butPos[i], color); } }
	}

	public void initDisplayCol(int color, int Col){
		//note(butPos[Col], color);
		
		for (int i = 2; i < 10; i++ ){
			note((Col + (i*12)), color);
		}
	}

	public void colorCycleAll(){
		for (int i = 0; i < 127; i++){
			initDisplay(i); 
			System.out.println("Color: "+i);
			try { Thread.sleep(300); } catch(InterruptedException ex) {;}}
	}

public static void readAndDisplaymazePath(String Path){ //String Path, int Msize
		int Msize = 9;
		String Steps[] = Path.split("\\r\\n|\\n|\\r");
		for(int i=0; i < Steps.length; i++){
			String CordinateS[] = Steps[i].replaceAll("[^\\d ]", "").split(" ");
			if (CordinateS.length == 2){
			int c1 =Integer.parseInt(CordinateS[0])*Msize + Integer.parseInt(CordinateS[1]);
			note(butPos[c1], 60); //127 yellow
			} else {System.out.println("Error:" + Steps[i] + ":"+ Arrays.toString(CordinateS));}
		} //end for
	} //end readAndDisplaymazePath

	public static void coolColors(){
		int color = 0;
		while (color < 400){
		//127 yellow
		for (int i = 0; i < butPos.length-1; i++){ note(butPos[i], color%127); color++; }
		try { Thread.sleep(300); } catch(InterruptedException ex) {;} //sleeps
		for (int i = butPos.length-1; i > 0; i--){ note(butPos[i], color%127); color++; }
		try { Thread.sleep(300); } catch(InterruptedException ex) {;} //sleeps
		}
	} //end coolColors

	public static void note(int note, int color)	{ //Int[]
		if (note == 200){return;}
		int	nChannel = 0;
		int	nKey = note;	// MIDI key number
		//int nVelocity=127;
		int nVelocity= color;
		//int nDuration = 0;
		//int nArgumentIndexOffset = 0;
		String	strDeviceName = "Touch [hw:1,0,0]"; //2

		//nKey = Integer.parseInt(args[0 + nArgumentIndexOffset]);
		//nKey = Math.min(127, Math.max(0, note);
		//nVelocity = Integer.parseInt(args[1 + nArgumentIndexOffset]);
		//nVelocity = Math.min(127, Math.max(0, nVelocity));
		//nDuration = Integer.parseInt(args[2 + nArgumentIndexOffset]);
		//nDuration = Math.max(0, nDuration);

		MidiDevice outputDevice = null;
		Receiver receiver = null;
		if (strDeviceName != null) {
			MidiDevice.Info	info = MidiCommon.getMidiDeviceInfo(strDeviceName, true);
			/*
			if (info == null) {
				System.out.println("no device info found for name " + strDeviceName);
				System.exit(1);
			}
			*/
			try {
				outputDevice = MidiSystem.getMidiDevice(info);
				if (DEBUG) System.out.println("MidiDevice: " + outputDevice);
				outputDevice.open();
			}
			catch (MidiUnavailableException e) {
				if (DEBUG) e.printStackTrace();
			}
			if (outputDevice == null) { System.err.println("no MidiDevice"); System.exit(1); }
			try { receiver = outputDevice.getReceiver(); }
			catch (MidiUnavailableException e) { if (DEBUG) e.printStackTrace(); }
		}
		else {
			/*	We retrieve a Receiver for the default
				MidiDevice.
			*/
			try { receiver = MidiSystem.getReceiver(); }
			catch (MidiUnavailableException e) {
				if (DEBUG) { e.printStackTrace(); }
			}
		}
		if (receiver == null) {
			System.out.println("wasn't able to retrieve Receiver");
			System.exit(2);
		}

		if (DEBUG) System.out.println("Receiver: " + receiver);
		/*	Here, we prepare the MIDI messages to send.
			Obviously, one is for turning the key on and
			one for turning it off.
		*/
		ShortMessage	onMessage = null;
		try {
			onMessage = new ShortMessage();
			onMessage.setMessage(ShortMessage.NOTE_ON, nChannel, nKey, nVelocity);
			//onMessage.setMessage("Any String you want".getBytes(), nVelocity);
			
		} catch (InvalidMidiDataException e) { if (DEBUG) { e.printStackTrace(); } }
		
		receiver.send(onMessage, -1);

		//receiver.close();
		//if (outputDevice != null) { System.err.println("Close failed, closing again"); outputDevice.close(); }
	} 
} //end MidiNote Class

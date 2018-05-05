//

import java.io.IOException;

import javax.sound.midi.Transmitter;
import javax.sound.midi.Receiver;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class LaunchPadProcess {
	public static MidiDevice.Info	info;
public static void main(String[] args) throws Exception {
String	strDeviceName =  "Touch [hw:1,0,0]"; 
		boolean bUseDefaultSynthesizer = false;
		info = getMidiDeviceInfo(strDeviceName, false);
		MidiDevice inputDevice = null;
		try {
				inputDevice = MidiSystem.getMidiDevice(info);
				inputDevice.open();
			}
		catch (MidiUnavailableException e) {
				logger(e);
				System.err.println("Here");
			}
		catch (NullPointerException e) {
				logger(e);
				System.err.println("No device detected");
				inputDevice = null;
			}
		if (inputDevice == null) {
				logger("wasn't able to retrieve MidiDevice");
				//Try to recover at this point or just restart the process? carefull of a fork bomb
				System.exit(1);
			}
		Receiver r = new DumpReceiver(System.out);
		try	{
				Transmitter	t = inputDevice.getTransmitter();
				t.setReceiver(r);
			}
		catch (MidiUnavailableException e) {
				logger("wasn't able to connect the device's Transmitter to the Receiver:");
				logger(e); 
				inputDevice.close();
				System.exit(1);
			}
		if (bUseDefaultSynthesizer)	{
			    Synthesizer synth = MidiSystem.getSynthesizer();
			    synth.open();
			    r = synth.getReceiver();
				try	{
						Transmitter	t = inputDevice.getTransmitter();
						t.setReceiver(r);
				}
				catch (MidiUnavailableException e) {
						logger("wasn't able to connect the device's Transmitter to the default Synthesizer:");
						logger(e); 
						inputDevice.close();
						System.exit(1);
					}
			}

		try { 
				System.out.println("Press Enter to Kill process");
				System.in.read();
				System.out.println("Bye!");
		}
		catch (IOException ioe) { ;	}
		inputDevice.close();
		try { Thread.sleep(1); }
		catch (InterruptedException e) { logger(e); }
	} //end main
private static void logger(String strMessage){ System.out.println(strMessage);	} //future logger to a file or socket or something
//#sudo mknod testCharDev c 89 1 // #permissions and associate with something?
private static void logger(Throwable t){ t.printStackTrace(); }


/** Retrieve a MidiDevice.Info for a given name.
 * 	was a part from jsresources.org
 *	Copyright (c) 1999 - 2001 by Matthias Pfisterer
 * 	Copyright (c) 2003 by Florian Bomers

This method tries to return a MidiDevice.Info whose name
matches the passed name. If no matching MidiDevice.Info is
found, null is returned.  If bForOutput is true, then only
output devices are searched, otherwise only input devices.

@param strDeviceName the name of the device for which an info
object should be retrieved.

@param bForOutput If true, only output devices are
considered. If false, only input devices are considered.

@return A MidiDevice.Info object matching the passed device
name or null if none could be found.

*/
public static MidiDevice.Info getMidiDeviceInfo(String strDeviceName, boolean bForOutput){
	MidiDevice.Info[]	aInfos = MidiSystem.getMidiDeviceInfo();
	for (int i = 0; i < aInfos.length; i++)
	{
		if (aInfos[i].getName().equals(strDeviceName))
		{
			try
			{
				MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
				boolean	bAllowsInput = (device.getMaxTransmitters() != 0);
				boolean	bAllowsOutput = (device.getMaxReceivers() != 0);
				if ((bAllowsOutput && bForOutput) || (bAllowsInput && !bForOutput))
				{
					return aInfos[i];
				}
			}
			catch (MidiUnavailableException e)
			{
				// TODO:
			}
		}
	}
	return null;
}
} //end class

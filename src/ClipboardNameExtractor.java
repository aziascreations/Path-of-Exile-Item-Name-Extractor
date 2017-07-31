import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClipboardNameExtractor {
	private static String lastData = "SuccMe", itemName = "ERROR";
	private static boolean isMuted = false;
	private static Runnable notifSound;
	
	public static void main(String[] args) {
		List<String> list = Arrays.asList(args);
		if(list.contains("--mute") | list.contains("--muted") | list.contains("-m"))
			isMuted = true;
		
		System.out.println("╔═══════════─ Path Of Exile Utilities ─══════════╗");
		System.out.println("║ Clipboard Item Name Extractor           v1.3.0 ║");
		System.out.println("╠═════════════════─ How To Use ─═════════════════╣");
		System.out.println("║ Just hit CTRL+C while overing an item and wait ║");
		System.out.println("║  for the beep.   After that, you can paste the ║");
		System.out.println("║  item's name without the stats.                ║");
		System.out.println("║ Copy it a second time to keep the stats.       ║");
		System.out.println("╚════════════════════════════════════════════════╝\n");
		
		//notifSound = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand");
		notifSound = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
		try {
			while(true) {
				if(readClipboard()) {
					copyItemName();
				}
				pause(2000L);
			}
		} catch(IllegalStateException e) {
			System.err.println("Error: Unable to open the clipboard.\n\nPlease restart the program.");
		}
	}
	
	public static void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch(InterruptedException e) {
			
		}
	}
	
	public static boolean readClipboard() {
		try {
			String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			if(lastData.equals(data)) {
				return false;
			} else {
				lastData = new String(data);
				if(!data.startsWith("Rarity"))
					return false;
				
				data = data.split("--------")[0];
				data = data.substring(data.indexOf('\n') + 1);
				data = data.replace("\n", " ");
				System.out.println(data);
				itemName = data;
				return true;
			}
		} catch(HeadlessException | UnsupportedFlavorException | IOException e) {
			return false;
		}
	}
	
	public static void copyItemName() {
		StringSelection selection = new StringSelection(itemName);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		
		if(notifSound != null && !isMuted)
			notifSound.run();
	}
}

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.atomic.AtomicReference;

public class SortingComponent extends JComponent {
    private final int width;
    private final int height;
    int channel = 1;
    public int volume = 75;
    MidiChannel[] channels = null;
    Synthesizer synth = null;
    int current;
    private int[] array;
    public AtomicReference<Thread> sorterThread = new AtomicReference<>();
    public SortingComponent(int width, int height, int[] array, AtomicReference<Thread> sorterThread) {
        this.width = width;
        this.height = height;
        this.array = array;
        this.sorterThread = sorterThread;

        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHints(rh);




        //Add lines to screen
        for (int i = 0; i < this.array.length; i++) {
            Rectangle2D.Double component = new Rectangle2D.Double((((double) this.width / (array.length + 50)) * 25) + (i*10), this.height - this.array[i], (double) this.width / (array.length + 50), this.array[i]);
            g2d.setColor(Color.RED);
            g2d.fill(component);

            int note = (int) (this.array[i] / 2.5);

            if(i == current) {
                g2d.setColor(Color.BLUE);
                g2d.fill(component);
            }
        }
    }

    public void updateComponents(int[] array, int current) {

        if(sorterThread.get() != null && sorterThread.get().isInterrupted()) {
            return;
        }

        this.current = current;
        this.array = array;
        this.repaint();

        // Play sound based on the current element
        int note = current / 3;
        channels[channel].noteOn(note, volume);
        try {
            Thread.sleep(1); // Adjust duration as needed
        } catch (InterruptedException e) {
            return;
        }
        channels[channel].noteOff(note);
    }

}

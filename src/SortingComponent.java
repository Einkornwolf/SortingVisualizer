import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.atomic.AtomicReference;

public class SortingComponent extends JComponent {
    int channel = 1;
    public int volume = 75;
    MidiChannel[] channels = null;
    Synthesizer synth = null;
    int speed = 50;
    int current;
    public int[] array;
    public AtomicReference<Thread> sorterThread;
    private final Timer timer;

    public SortingComponent(int[] array, AtomicReference<Thread> sorterThread) {
        this.array = array;
        this.sorterThread = sorterThread;

        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

        timer = new Timer(speed, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(rh);

        int maxValue = 0;
        for (int value : this.array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        double margin = getWidth() / 8.0;
        double rectWidth = (getWidth() - 2 * margin) / this.array.length;
        double startX = margin;

        for (int i = 0; i < this.array.length; i++) {
            double scaledHeight = ((double) this.array[i] / maxValue) * getHeight();
            Rectangle2D component = new Rectangle2D.Double(
                    startX + i * rectWidth,
                    getHeight() - scaledHeight,
                    rectWidth,
                    scaledHeight);

            g2d.setColor(Color.RED);
            g2d.fill(component);

            if (i == current) {
                g2d.setColor(Color.BLUE);
                g2d.fill(component);
            }
        }
    }

    // Add this method to calculate the frequency based on the height
    private double calculateFrequency(int value, int maxValue) {
        double minFrequency = 50.0; // Minimum frequency in Hz
        double maxFrequency = 450.0; // Maximum frequency in Hz
        return minFrequency + ((double) value / maxValue) * (maxFrequency - minFrequency);
    }

    // Add this method to set the MIDI instrument to a synth
    private void setSynthInstrument(MidiChannel channel) {
        int synthInstrument = 82; // Synth instrument (Lead 1 (square))
        channel.programChange(synthInstrument);
    }

    // Update the updateComponents method
    public void updateComponents(int[] array, int current) {
        if (sorterThread.get() != null && sorterThread.get().isInterrupted()) {
            return;
        }

        this.current = current;
        this.array = array;

        // Calculate the frequency based on the current element's height
        int maxValue = 0;
        for (int value : this.array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        double frequency = calculateFrequency(array[Math.max(current, 0)], maxValue);

        // Play sound based on the calculated frequency
        int note = (int) (69 + 12 * Math.log(frequency / 440.0) / Math.log(2)); // Convert frequency to MIDI note
        setSynthInstrument(channels[channel]); // Set the instrument to synth
        channels[channel].noteOn(note, volume);
        try {
            Thread.sleep(speed); // Adjust duration as needed
        } catch (InterruptedException e) {
            return;
        }
        channels[channel].noteOff(note);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        timer.setDelay(Math.max(speed, 1));
    }
}
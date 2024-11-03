// Main.java
import javax.swing.*;

import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        AtomicReference<Thread> sorterThread = new AtomicReference<>();
        
        JFrame frame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final int[][] array = {new int[100]};
        for (int i = 0; i < array[0].length; i++) {
            array[0][i] = i * 7;
        }

        SortingComponent sortingComponent = new SortingComponent(array[0], sorterThread);

        // Create Controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3));
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setPreferredSize(new Dimension(screenSize.width, 50));


        // Create Selector
        JComboBox<String> selector = new JComboBox<>();
        selector.addItem("Bubble Sort");
        selector.addItem("Selection Sort");
        selector.addItem("Insertion Sort");
        selector.addItem("Bogo Sort");
        selector.addItem("Quick Sort");
        selector.addItem("Miracle Sort");
        selector.addItem("Bozo Sort");
        selector.addItem("Stooge Sort");
        selector.addItem("Merge Sort");
        selector.addItem("Heap Sort");
        selector.addItem("Radix Sort");
        selector.addItem("Shell Sort");
        selector.addItem("Cocktail Sort");
        selector.addItem("Gnome Sort");
        selector.addItem("Comb Sort");
        selector.addItem("Pigeonhole Sort");
        selector.addItem("Risk Sort");
        selector.addItem("Intro Sort");
        selector.addItem("Cycle Sort");
        selector.addItem("Block Sort");
        selector.addItem("Bucket Sort");
        selector.addItem("Flash Sort");
        selector.addItem("Spread Sort");
        selector.addItem("Tournament Sort");
        selector.addItem("Patience Sort");
        selector.addItem("Strand Sort");
        selector.addItem("Bitonic Sort");
        selector.addItem("Pancake Sort");
        selector.addItem("Tim Sort");

        selector.setBackground(Color.BLACK);
        selector.setForeground(Color.WHITE);
        selector.setFocusable(false);
        selector.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        //Exit Button
        JButton stopButton = new JButton("Exit");
        stopButton.setBackground(Color.BLACK);
        stopButton.setForeground(Color.WHITE);
        stopButton.setFocusPainted(false);
        stopButton.setBorderPainted(false);
        stopButton.setContentAreaFilled(false);
        stopButton.setOpaque(true);
        stopButton.addActionListener(e -> {
            System.exit(0);
        });

        //Create amount of items slider
        JSlider jSlider2 = new JSlider(JSlider.HORIZONTAL);
        jSlider2.setBackground(Color.BLACK);
        jSlider2.setForeground(Color.WHITE);
        jSlider2.setOpaque(true);
        // paint the ticks and tracks
        jSlider2.setPaintTrack(true);
        jSlider2.setPaintTicks(true);
        jSlider2.setPaintLabels(true);
        jSlider2.setFocusable(false);

        // set spacing
        jSlider2.setMajorTickSpacing(250);
        jSlider2.setMinorTickSpacing(250);
        jSlider2.setMaximum(5);
        jSlider2.setMaximum(1000);
        // Update the amount of items slider change listener
        jSlider2.addChangeListener(e -> {
            int newSize = jSlider2.getValue();
            int[] newArray = new int[newSize];
            for (int i = 0; i < newSize; i++) {
                newArray[i] = i * 7; // or any other logic to initialize the array
            }
            sortingComponent.array = newArray;
            sortingComponent.repaint();
        });

        //Sorting Speed Slider
        JSlider jSlider3 = new JSlider(JSlider.HORIZONTAL);
        jSlider3.setBackground(Color.BLACK);
        jSlider3.setForeground(Color.WHITE);
        jSlider3.setOpaque(true);
        // paint the ticks and tracks
        jSlider3.setPaintTrack(true);
        jSlider3.setPaintTicks(true);
        jSlider3.setPaintLabels(true);
        
        // set spacing
        jSlider3.setMajorTickSpacing(10);
        jSlider3.setMinorTickSpacing(10);
        jSlider3.setMaximum(100);
        jSlider3.setValue(1);
        jSlider3.addChangeListener(e -> {
            int sliderValue = jSlider3.getValue();
            int newSpeed = (int) (10 / (Math.log(sliderValue + 1) + 1)); // Further adjusted inverse logarithmic speed calculation for higher speeds
            sortingComponent.setSpeed(newSpeed);
        });
        
        //Stop Current Sort Button
        JButton stopSortButton = new JButton("Stop Sorting");
        stopSortButton.setBackground(Color.BLACK);
        stopSortButton.setForeground(Color.WHITE);
        stopSortButton.setFocusPainted(false);
        stopSortButton.setBorderPainted(false);
        stopSortButton.setContentAreaFilled(false);
        stopSortButton.setOpaque(true);
        stopSortButton.addActionListener(e -> {
            //Kill the current thread
            if (sorterThread.get() != null) {
                for(int i = 0; i < 1000; i++) {
                    sorterThread.get().interrupt();
                }
            }

            jSlider2.setEnabled(true);

        });

        //Create Volume Slider
        JSlider jSlider = new JSlider(JSlider.HORIZONTAL);
        jSlider.setBackground(Color.BLACK);
        jSlider.setForeground(Color.WHITE);
        jSlider.setOpaque(true);
        // paint the ticks and tracks
        jSlider.setPaintTrack(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        // set spacing
        jSlider.setMajorTickSpacing(10);
        jSlider.setMinorTickSpacing(10);
        jSlider.addChangeListener(e -> {
            sortingComponent.volume = jSlider.getValue();
        });

        JLabel volume = new JLabel("Volume: ");
        volume.setSize(30, 20);
        volume.setBackground(Color.BLACK);
        volume.setForeground(Color.WHITE);
        volume.setOpaque(true);

        frame.setResizable(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                sortingComponent.setSize(frame.getSize());
                sortingComponent.repaint();
            }
        });


        //Start Button
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setOpaque(true);
        startButton.addActionListener(e -> {

            jSlider2.setEnabled(false);

            array[0] = sortingComponent.array;

             sorterThread.set(new Thread(() -> {

                 // Randomize Array
                 for (int i = 0; i < array[0].length; i++) {
                     int randomIndexToSwap = (int) (Math.random() * array[0].length);
                     int temp = array[0][randomIndexToSwap];
                     array[0][randomIndexToSwap] = array[0][i];
                     array[0][i] = temp;
                 }

                 sortingComponent.updateComponents(array[0], 0);

                 // Get Sorting Type
                 String currentSort = (String) selector.getSelectedItem();

                 // Switch
                 switch (currentSort) {
                     case "Bubble Sort":
                         bubbleSort(array[0], sortingComponent);
                         break;
                     case "Selection Sort":
                         selectionSort(array[0], sortingComponent);
                         break;
                     case "Insertion Sort":
                         insertionSort(array[0], sortingComponent);
                         break;
                     case "Bogo Sort":
                         bogoSort(array[0], sortingComponent);
                         break;
                     case "Quick Sort":
                         quickSort(array[0], 0, array[0].length - 1, sortingComponent);
                         break;
                     case "Miracle Sort":
                         miracleSort(array[0], sortingComponent);
                         break;
                     case "Bozo Sort":
                         bozoSort(array[0], sortingComponent);
                         break;
                     case "Stooge Sort":
                         stoogeSort(array[0], 0, array[0].length - 1, sortingComponent);
                         break;
                     case "Merge Sort":
                         mergeSort(array[0], 0, array[0].length - 1, sortingComponent);
                         break;
                     case "Heap Sort":
                         heapSort(array[0], sortingComponent);
                         break;
                     case "Radix Sort":
                         radixSort(array[0], sortingComponent);
                         break;
                     case "Shell Sort":
                         shellSort(array[0], sortingComponent);
                         break;
                     case "Cocktail Sort":
                         cocktailSort(array[0], sortingComponent);
                         break;
                     case "Gnome Sort":
                         gnomeSort(array[0], sortingComponent);
                         break;
                     case "Comb Sort":
                         combSort(array[0], sortingComponent);
                         break;
                     case "Pigeonhole Sort":
                         pigeonholeSort(array[0], sortingComponent);
                         break;
                     case "Risk Sort":
                         riskSort(array[0], sortingComponent);
                         break;
                     case "Intro Sort":
                         introSort(array[0], 0, array[0].length - 1, (int) (2 * Math.floor(Math.log(array[0].length) / Math.log(2))), sortingComponent);
                         break;
                     case "Cycle Sort":
                         cycleSort(array[0], sortingComponent);
                         break;
                     case "Block Sort":
                         blockSort(array[0], sortingComponent);
                         break;
                     case "Bucket Sort":
                         bucketSort(array[0], sortingComponent);
                         break;
                     case "Flash Sort":
                         flashSort(array[0], sortingComponent);
                         break;
                     case "Spread Sort":
                         spreadSort(array[0], sortingComponent);
                         break;
                     case "Tournament Sort":
                         tournamentSort(array[0], sortingComponent);
                         break;
                     case "Patience Sort":
                         patienceSort(array[0], sortingComponent);
                         break;
                     case "Strand Sort":
                         strandSort(array[0], sortingComponent);
                         break;
                     case "Bitonic Sort":
                         bitonicSort(array[0], sortingComponent);
                         break;
                     case "Pancake Sort":
                         pancakeSort(array[0], sortingComponent);
                         break;
                     case "Tim Sort":
                    	 timSort(array[0], sortingComponent);
                    	 break;
                 }

                 //Go through the array and play the notes
                 for (int i = 0; i < array[0].length; i++) {
                     array[0][i] = i * 7;
                     sortingComponent.updateComponents(array[0], i);

                 }

                    jSlider2.setEnabled(true);

             }));

            sorterThread.get().start();

            sortingComponent.sorterThread.set(sorterThread.get());
        });

        frame.setSize(screenSize);
        frame.setTitle("Sorting Algorithm Visualizer");
        frame.getContentPane().setBackground(Color.BLACK);

        frame.add(controlPanel, BorderLayout.NORTH);
        controlPanel.add(startButton);
        controlPanel.add(stopSortButton);
        controlPanel.add(selector);
        controlPanel.add(stopButton);
        controlPanel.add(volume);
        controlPanel.add(jSlider);
        controlPanel.add(jSlider2);
        controlPanel.add(jSlider3);
        frame.add(sortingComponent);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


		//Implement Tim Sort

private static void timSort(int[] array, SortingComponent sortingComponent) {
    int RUN = 32;
    int n = array.length;

    // Sort individual subarrays of size RUN
    for (int i = 0; i < n; i += RUN) {
        insertionSort(array, i, Math.min((i + 31), (n - 1)), sortingComponent);
    }

    // Merge subarrays in a bottom-up manner
    for (int size = RUN; size < n; size = 2 * size) {
        for (int left = 0; left < n; left += 2 * size) {
            int mid = left + size - 1;
            int right = Math.min((left + 2 * size - 1), (n - 1));
            merge(array, left, mid, right, sortingComponent);
        }
    }
}

private static void insertionSort(int[] array, int left, int right, SortingComponent sortingComponent) {
    for (int i = left + 1; i <= right; i++) {
        int temp = array[i];
        int j = i - 1;
        while (j >= left && array[j] > temp) {
            array[j + 1] = array[j];
            j--;
            if (sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, j);
        }
        array[j + 1] = temp;
        if (sortingComponent.sorterThread.get().isInterrupted()) return;
        sortingComponent.updateComponents(array, j + 1);
    }
}

	

	private static void pancakeSort(int[] array, SortingComponent sortingComponent) {
        for (int i = array.length; i > 1; i--) {
            int maxIndex = findMax(array, i);
            if (maxIndex != i - 1) {
                flip(array, maxIndex);
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, maxIndex);

                flip(array, i - 1);
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, i - 1);

            }
        }
    }

    private static void flip(int[] array, int maxIndex) {
        int start = 0;
        while (start < maxIndex) {
            int temp = array[start];
            array[start] = array[maxIndex];
            array[maxIndex] = temp;
            start++;
            maxIndex--;
        }
    }

    private static int findMax(int[] array, int i) {
        int maxIndex = 0;
        for (int j = 0; j < i; j++) {
            if (array[j] > array[maxIndex]) {
                maxIndex = j;
            }
        }
        return maxIndex;
    }

    private static void bitonicSort(int[] array, SortingComponent sortingComponent) {
        bitonicSort(array, 0, array.length, 1, sortingComponent);
    }

    private static void bitonicSort(int[] array, int low, int count, int dir, SortingComponent sortingComponent) {
        if (count > 1) {
            int k = count / 2;
            bitonicSort(array, low, k, 1, sortingComponent);
            bitonicSort(array, low + k, k, 0, sortingComponent);
            bitonicMerge(array, low, count, dir, sortingComponent);
        }
    }

    private static void bitonicMerge(int[] array, int low, int count, int dir, SortingComponent sortingComponent) {
        if (count > 1) {
            int k = count / 2;
            for (int i = low; i < low + k; i++) {
                if ((dir == 1 && array[i] > array[i + k]) || (dir == 0 && array[i] < array[i + k])) {
                    int temp = array[i];
                    array[i] = array[i + k];
                    array[i + k] = temp;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, i);

                }
            }
            bitonicMerge(array, low, k, dir, sortingComponent);
            bitonicMerge(array, low + k, k, dir, sortingComponent);
        }
    }

    private static void strandSort(int[] array, SortingComponent sortingComponent) {
        List<Integer> sorted = new ArrayList<>();
        List<Integer> unsorted = new ArrayList<>();
        for (int i : array) {
            unsorted.add(i);
        }
        while (!unsorted.isEmpty()) {
            List<Integer> sublist = new ArrayList<>();
            sublist.add(unsorted.remove(0));
            for (int i = 0; i < unsorted.size(); i++) {
                if (unsorted.get(i) > sublist.get(sublist.size() - 1)) {
                    sublist.add(unsorted.remove(i));
                    i--;
                }
            }
            List<Integer> newSorted = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i < sorted.size() && j < sublist.size()) {
                if (sorted.get(i) < sublist.get(j)) {
                    newSorted.add(sorted.get(i));
                    i++;
                } else {
                    newSorted.add(sublist.get(j));
                    j++;
                }
            }
            while (i < sorted.size()) {
                newSorted.add(sorted.get(i));
                i++;
            }
            while (j < sublist.size()) {
                newSorted.add(sublist.get(j));
                j++;
            }
            sorted = newSorted;
            for (int k = 0; k < sorted.size(); k++) {
                array[k] = sorted.get(k);
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, k);

            }
        }
    }

    private static void patienceSort(int[] array, SortingComponent sortingComponent) {
        List<Stack<Integer>> piles = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            int card = array[i];
            int pile = 0;
            for (pile = 0; pile < piles.size(); pile++) {
                if (piles.get(pile).peek() >= card) {
                    break;
                }
            }
            if (pile == piles.size()) {
                piles.add(new Stack<>());
            }
            piles.get(pile).push(card);
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);

        }

        int index = 0;
        PriorityQueue<Stack<Integer>> heap = new PriorityQueue<>(Comparator.comparingInt(stack -> stack.peek()));
        heap.addAll(piles);

        while (!heap.isEmpty()) {
            Stack<Integer> smallestPile = heap.poll();
            array[index++] = smallestPile.pop();
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, index - 1);

            if (!smallestPile.isEmpty()) {
                heap.add(smallestPile);
            }
        }
    }

    private static void tournamentSort(int[] array, SortingComponent sortingComponent) {
        int n = array.length;
        int[] tree = new int[2 * n - 1];
        int[] index = new int[2 * n - 1];
        for (int i = 0; i < n; i++) {
            tree[n + i - 1] = array[i];
            index[n + i - 1] = i;
        }
        for (int i = n - 2; i >= 0; i--) {
            if (tree[2 * i + 1] > tree[2 * i + 2]) {
                tree[i] = tree[2 * i + 1];
                index[i] = index[2 * i + 1];
            } else {
                tree[i] = tree[2 * i + 2];
                index[i] = index[2 * i + 2];
            }
        }
        for (int i = 0; i < n; i++) {
            int j = 0;
            while (j < n - 1) {
                if (tree[2 * j + 1] > tree[2 * j + 2]) {
                    tree[j] = tree[2 * j + 1];
                    index[j] = index[2 * j + 1];
                } else {
                    tree[j] = tree[2 * j + 2];
                    index[j] = index[2 * j + 2];
                }
                j = 2 * j + 1;
            }
            array[i] = tree[0];
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);

            tree[n + index[0] - 1] = Integer.MIN_VALUE;
            int k = n + index[0] - 1;
            while (k > 0) {
                k = (k - 1) / 2;
                if (tree[2 * k + 1] > tree[2 * k + 2]) {
                    tree[k] = tree[2 * k + 1];
                    index[k] = index[2 * k + 1];
                } else {
                    tree[k] = tree[2 * k + 2];
                    index[k] = index[2 * k + 2];
                }
            }
        }
    }

    private static void spreadSort(int[] array, SortingComponent sortingComponent) {
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int range = max - min + 1;
        int[] L = new int[range];
        for (int i = 0; i < array.length; i++) {
            L[array[i] - min]++;
        }
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (L[i] > 0) {
                array[index] = i + min;
                L[i]--;
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, index);

                index++;
            }
        }
    }

    private static void flashSort(int[] array, SortingComponent sortingComponent) {
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int range = max - min + 1;
        int[] L = new int[range];
        for (int i = 0; i < array.length; i++) {
            L[array[i] - min]++;
        }
        for (int i = 1; i < range; i++) {
            L[i] += L[i - 1];
        }
        int[] buffer = new int[array.length];
        System.arraycopy(array, 0, buffer, 0, array.length);
        for (int k = array.length - 1; k >= 0; k--) {
            int index = --L[buffer[k] - min];
            array[index] = buffer[k];
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, index);

        }
    }

    private static void bucketSort(int[] array, SortingComponent sortingComponent) {
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int range = max - min + 1;
        int bucketCount = (int) Math.ceil(Math.sqrt(array.length));
        int bucketSize = (int) Math.ceil((double) range / bucketCount);
        int[][] buckets = new int[bucketCount][0];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new int[0];
        }
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (int) Math.floor((array[i] - min) / bucketSize);
            buckets[bucketIndex] = append(buckets[bucketIndex], array[i]);
        }
        int index = 0;
        for (int[] bucket : buckets) {
            insertionSort(bucket, sortingComponent);
            for (int j = 0; j < bucket.length; j++) {
                array[index] = bucket[j];
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, index);

                index++;
            }
        }
    }

    private static void blockSort(int[] array, SortingComponent sortingComponent) {
        int max = Arrays.stream(array).max().getAsInt();
        int min = Arrays.stream(array).min().getAsInt();
        int range = max - min + 1;
        int bucketSize = 10;
        int bucketCount = (int) Math.ceil((double) range / bucketSize);
        int[][] buckets = new int[bucketCount][0];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new int[0];
        }
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (int) Math.floor((array[i] - min) / bucketSize);
            buckets[bucketIndex] = append(buckets[bucketIndex], array[i]);
        }
        int index = 0;
        for (int[] bucket : buckets) {
            insertionSort(bucket, sortingComponent);
            for (int j = 0; j < bucket.length; j++) {
                array[index] = bucket[j];
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, index);

                index++;
            }
        }
    }

    private static int[] append(int[] bucket, int i) {
        int[] temp = new int[bucket.length + 1];
        System.arraycopy(bucket, 0, temp, 0, bucket.length);
        temp[temp.length - 1] = i;
        return temp;
    }


    private static void cycleSort(int[] array, SortingComponent sortingComponent) {
        int writes = 0;
        for (int cycleStart = 0; cycleStart <= array.length - 2; cycleStart++) {
            int item = array[cycleStart];
            int pos = cycleStart;
            for (int i = cycleStart + 1; i < array.length; i++) {
                if (array[i] < item) {
                    pos++;
                }
            }
            if (pos == cycleStart) {
                continue;
            }
            while (item == array[pos]) {
                pos += 1;
            }
            if (pos != cycleStart) {
                int temp = item;
                item = array[pos];
                array[pos] = temp;
                writes++;
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, pos);

            }
            while (pos != cycleStart) {
                pos = cycleStart;
                for (int i = cycleStart + 1; i < array.length; i++) {
                    if (array[i] < item) {
                        pos += 1;
                    }
                }
                while (item == array[pos]) {
                    pos += 1;
                }
                if (item != array[pos]) {
                    int temp = item;
                    item = array[pos];
                    array[pos] = temp;
                    writes++;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, pos);

                }
            }
        }
    }


    private static void introSort(int[] array, int i, int i1, int i2, SortingComponent sortingComponent) {
        if (i1 - i > 16) {
            if (i2 == 0) {
                heapSort(array, sortingComponent);
            } else {
                int pivot = partition(array, i, i1, sortingComponent);
                introSort(array, i, pivot, i2 - 1, sortingComponent);
                introSort(array, pivot + 1, i1, i2 - 1, sortingComponent);
            }
        } else {
            insertionSort(array, sortingComponent);
        }
    }

    private static void riskSort(int[] array, SortingComponent sortingComponent) {
        //Shuffle array 1 time
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = (int) (Math.random() * array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }

        if(!isSorted(array)) System.exit(3);
    }

    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static void pigeonholeSort(int[] array, SortingComponent sortingComponent) {
        int min = Arrays.stream(array).min().getAsInt();
        int max = Arrays.stream(array).max().getAsInt();
        int range = max - min + 1;
        int[] holes = new int[range];
        for (int i = 0; i < array.length; i++) {
            holes[array[i] - min]++;
        }
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (holes[i] > 0) {
                holes[i]--;
                array[index] = i + min;
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, index);

                index++;
            }
        }
    }

    private static void bubbleSort(int[] array, SortingComponent sortingComponent) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, j);

                }
            }
        }
    }

    private static void selectionSort(int[] array, SortingComponent sortingComponent) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);
        }
    }

    private static void insertionSort(int[] array, SortingComponent sortingComponent) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, j);

            }
            array[j + 1] = key;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, j + 1);

        }
    }

    private static void bogoSort(int[] array, SortingComponent sortingComponent) {
            while (!isSorted(array)) {
                for (int i = 0; i < array.length; i++) {
                    int randomIndexToSwap = (int) (Math.random() * array.length);
                    int temp = array[randomIndexToSwap];
                    array[randomIndexToSwap] = array[i];
                    array[i] = temp;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, i);

                }
            }
    }

    private static void quickSort(int[] array, int low, int high, SortingComponent sortingComponent) {
        if (low < high) {
            int pi = partition(array, low, high, sortingComponent);
            quickSort(array, low, pi - 1, sortingComponent);
            quickSort(array, pi + 1, high, sortingComponent);
        }
    }

    private static int partition(int[] array, int low, int high, SortingComponent sortingComponent) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                if(sortingComponent.sorterThread.get().isInterrupted()) return 0;
                sortingComponent.updateComponents(array, j);

            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        if(sortingComponent.sorterThread.get().isInterrupted()) return 0;
        sortingComponent.updateComponents(array, high);

        return i + 1;
    }

    private static void miracleSort(int[] array, SortingComponent sortingComponent) {
        while (!isSorted(array)) {
            sortingComponent.updateComponents(array, 0);

        }
    }

    private static void bozoSort(int[] array, SortingComponent sortingComponent) {
        while (!isSorted(array)) {
            int randomIndexToSwap = (int) (Math.random() * array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[0];
            array[0] = temp;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, 0);

        }
    }

    private static void stoogeSort(int[] array, int i, int i1, SortingComponent sortingComponent) {
        if (array[i] > array[i1]) {
            int temp = array[i];
            array[i] = array[i1];
            array[i1] = temp;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);

        }
        if (i + 1 >= i1) {
            return;
        }
        int t = (i1 - i + 1) / 3;
        stoogeSort(array, i, i1 - t, sortingComponent);
        stoogeSort(array, i + t, i1, sortingComponent);
        stoogeSort(array, i, i1 - t, sortingComponent);
    }

    private static void mergeSort(int[] array, int i, int i1, SortingComponent sortingComponent) {
        if (i < i1) {
            int m = i + (i1 - i) / 2;
            mergeSort(array, i, m, sortingComponent);
            mergeSort(array, m + 1, i1, sortingComponent);
            merge(array, i, m, i1, sortingComponent);
        }
    }

    private static void merge(int[] array, int i, int m, int i1, SortingComponent sortingComponent) {
        int n1 = m - i + 1;
        int n2 = i1 - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int x = 0; x < n1; x++) {
            L[x] = array[i + x];
        }
        for (int y = 0; y < n2; y++) {
            R[y] = array[m + 1 + y];
        }

        int x = 0, y = 0;
        int k = i;
        while (x < n1 && y < n2) {
            if (L[x] <= R[y]) {
                array[k] = L[x];
                x++;
            } else {
                array[k] = R[y];
                y++;
            }
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, k);

            k++;
        }

        while (x < n1) {
            array[k] = L[x];
            x++;
            k++;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, k);

        }

        while (y < n2) {
            array[k] = R[y];
            y++;
            k++;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, k);

        }
    }

    private static void heapSort(int[] array, SortingComponent sortingComponent) {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapify(array, array.length, i, sortingComponent);
        }
        for (int i = array.length - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);

            heapify(array, i, 0, sortingComponent);
        }
    }

    private static void heapify(int[] array, int length, int i, SortingComponent sortingComponent) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < length && array[left] > array[largest]) {
            largest = left;
        }

        if (right < length && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);

            heapify(array, length, largest, sortingComponent);
        }
    }

    private static void radixSort(int[] array, SortingComponent sortingComponent) {
        int m = getMax(array, array.length);
        for (int exp = 1; m / exp > 0; exp *= 10) {
            countSort(array, array.length, exp, sortingComponent);
        }
    }

    private static void countSort(int[] array, int length, int exp, SortingComponent sortingComponent) {
        int[] output = new int[length];
        int[] count = new int[10];
        for (int i = 0; i < length; i++) {
            count[(array[i] / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = length - 1; i >= 0; i--) {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            count[(array[i] / exp) % 10]--;
        }
        for (int i = 0; i < length; i++) {
            array[i] = output[i];
            if(sortingComponent.sorterThread.get().isInterrupted()) return;
            sortingComponent.updateComponents(array, i);

        }
    }

    private static int getMax(int[] array, int length) {
        int max = array[0];
        for (int i = 1; i < length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private static void shellSort(int[] array, SortingComponent sortingComponent) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i += 1) {
                int temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, j);

                }
                array[j] = temp;
            }
        }
    }

    private static void cocktailSort(int[] array, SortingComponent sortingComponent) {
        boolean swapped = true;
        int start = 0;
        int end = array.length;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end - 1; ++i) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, i);

                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
            swapped = false;
            end = end - 1;
            for (int i = end - 1; i >= start; i--) {
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, i);

                    swapped = true;
                }
            }
            start = start + 1;
        }
    }

    private static void gnomeSort(int[] array, SortingComponent sortingComponent) {
        int index = 0;
        while (index < array.length) {
            if (index == 0) {
                index++;
            }
            if (array[index] >= array[index - 1]) {
                index++;
            } else {
                int temp = array[index];
                array[index] = array[index - 1];
                array[index - 1] = temp;
                if(sortingComponent.sorterThread.get().isInterrupted()) return;
                sortingComponent.updateComponents(array, index);

                index--;
            }
        }
    }

    private static void combSort(int[] array, SortingComponent sortingComponent) {
        int gap = array.length;
        boolean swapped = true;
        while (gap != 1 || swapped) {
            gap = getNextGap(gap);
            swapped = false;
            for (int i = 0; i < array.length - gap; i++) {
                if (array[i] > array[i + gap]) {
                    int temp = array[i];
                    array[i] = array[i + gap];
                    array[i + gap] = temp;
                    if(sortingComponent.sorterThread.get().isInterrupted()) return;
                    sortingComponent.updateComponents(array, i);

                    swapped = true;
                }
            }
        }
    }

    private static int getNextGap(int gap) {
        gap = (gap * 10) / 13;
        if (gap < 1) {
            return 1;
        }
        return gap;
    }
    }

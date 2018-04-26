package com.sg.tdgarage.event;

import com.sg.tdgarage.configure.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.PriorityQueue;

public class EventRecorder {
    private static PriorityQueue<Event> eventQueue = new PriorityQueue<>();

    public static void record(Event event) {
        eventQueue.add(event);
    }

    public static void print() {
        File file = new File(Constant.LOG_FILE_NAME);
        try {
            PrintStream out = new PrintStream(file);
            while (!eventQueue.isEmpty()) {
                Event event = eventQueue.poll();
                event.setPrintStream(out);
                event.action();
            }
            System.out.println(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

package com.sg.tdgarage.event;

import java.util.PriorityQueue;

public class EventDriver {
    private static PriorityQueue<Event> eventQueue = new PriorityQueue<>();

    public static void newEvent(Event event){
        eventQueue.add(event);
    }

    public static Event nextEvent(){
        return eventQueue.poll();
    }

    public static boolean hasNext() {
        return !eventQueue.isEmpty();
    }
}

package org.ezool.iqx.events;

import de.greenrobot.event.EventBus;

public class EventBusWrapper {
	
	private static EventBus centralEventBus = EventBus.getDefault();

	private EventBusWrapper() {}
	
	public static EventBus getEventBus()
	{
		return EventBusWrapper.centralEventBus;
	}
	
	public static void register(Object o)
	{
		EventBusWrapper.centralEventBus.register(o);
	}
	
	public static void unregister(Object o)
	{
		EventBusWrapper.centralEventBus.unregister(o);
	}
	
	public static void post(Object evt)
	{
		EventBusWrapper.centralEventBus.post(evt);
	}
}

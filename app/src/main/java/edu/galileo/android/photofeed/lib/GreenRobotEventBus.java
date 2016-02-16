package edu.galileo.android.photofeed.lib;

import edu.galileo.android.photofeed.lib.base.EventBus;

/**
 * Created by ykro.
 */
public class GreenRobotEventBus implements EventBus {
    de.greenrobot.event.EventBus eventBus;

    public GreenRobotEventBus(){
        eventBus = de.greenrobot.event.EventBus.getDefault();
    }

    public void register(Object subscriber){
        eventBus.register(subscriber);
    }

    public void unregister(Object subscriber){
        eventBus.unregister(subscriber);
    }

    public void post(Object event){
        eventBus.post(event);
    }
}

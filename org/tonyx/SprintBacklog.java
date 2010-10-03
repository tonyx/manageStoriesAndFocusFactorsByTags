package org.tonyx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 18-set-2010
 * Time: 23.10.39
 * To change this template use File | Settings | File Templates.
 */
public class SprintBacklog {
    private HashMap<String,Story> stories= new HashMap<String,Story>();
    private String _name;
    public SprintBacklog(String name) {
        _name = name;
    }

    public void add(Story story) {
        stories.put(story.getStoryName(),story);
    }

    public HashMap<String,Story> getStories() {
        return stories;
    }

    public void setDone(String storyName,int actual) {
        Story myStory = stories.get(storyName);
        if (myStory!=null)
        myStory.setActual(actual);
    }

    public double actualFocusFactor() throws Exception{
        double estimated=0.0;
        int actual=0;
        for (Story story: stories.values()) {
            if (story.isDone()) {
                estimated+=story.getInitialEstimate();
                actual+=story.getActual();
            }
        }
        return (estimated/(double)actual);
    }

    public double actualsByTag(String tag) {
        double total=0.0;
        for (Story story: stories.values()) {
            if (story.isDone()) {
                if (story.getTags().contains(tag)) {
                    total+=story.getActual();
                }
            }
        }
        return (total);
    }

    @Deprecated
    public double actualsByTags(String... tags) {
        double total=0.0;
        for (Story story: stories.values()) {
            if (story.isDone()) {
                for (String tag: tags) {
                    if (story.getTags().contains(tag)) {
                        total+=story.getActual();
                        break;
                    }
                }
            }
        }
        return (total);
    }

    public double actualFocusFactoryByTag(String tag) {
        double total=0.0;
        double estimated = 0.0;
        for (Story story: stories.values()) {
            if (story.isDone()) {
                if (story.getTags().contains(tag)) {
                    total+=story.getActual();
                    estimated+=story.getInitialEstimate();
                }
            }
        }
        return (estimated/total);
    }

    public double actualFocusFactoryByTag(String... tags) {
        Set<String> setTags = new HashSet<String>();
        for(String tag : tags) {
           setTags.add(tag);
        }
        double total=0.0;
        double estimated = 0.0;
        for (Story story: stories.values()) {
            if (story.isDone()) {
                if (story.getTags().containsAll(setTags)) {
                    total+=story.getActual();
                    estimated+=story.getInitialEstimate();
                }
            }
        }
        return (estimated/total);
    }



    public double actualFocusFactoryByPerfectMatchTag(String ... tags) {
        double total=0.0;
        double estimated = 0.0;
        HashSet<String> tagSet = new HashSet<String>();
        for(String tag: tags) {
            tagSet.add(tag);
        }
        for (Story story: stories.values()) {
            if (story.isDone()) {
                if (story.getTags().equals(tagSet)) {
                    total+=story.getActual();
                    estimated+=story.getInitialEstimate();
                }
            }
        }
        return (estimated/total);
    }


    public double actualsByPerfectMatchTags(String... tags) {
        double total=0.0;
        HashSet<String> tagSet = new HashSet<String>();
        for(String tag: tags) {
            tagSet.add(tag);
        }
        for (Story story: stories.values()) {
            if (story.isDone()) {
                if (story.getTags().equals(tagSet)) {
                    total+=story.getActual();
                }
            }
        }
        return total;
    }
}





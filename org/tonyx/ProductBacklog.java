package org.tonyx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 18-set-2010
 * Time: 22.12.07
 * To change this template use File | Settings | File Templates.
 */
public class ProductBacklog {
    private HashMap<String,Story> stories= new HashMap<String,Story>();
    private String _name;
    public ProductBacklog(String name) {
        _name = name;
    }

    public void add(Story story) {
        stories.put(story.getStoryName(),story);
    }

    public HashMap<String,Story> getStories() {
        return stories;
    }

    public SprintBacklog produceSprint(String sprintName, String ... tags) {
        SprintBacklog sprint = new SprintBacklog(sprintName);
        for (String tag: tags) {
            Story story = stories.get(tag);
            if (story!=null)
                sprint.add(story);
        }
        return sprint;
    }
    
}

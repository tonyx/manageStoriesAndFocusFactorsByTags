package org.tonyx;

import java.util.HashSet;

public class StoriesFactory {
    private HashSet<String> storyNames  = new HashSet();

    public Story createStory(String storyName, int initialEstimate) throws Exception {
        if (storyNames.contains(storyName))
            throw new Exception("cannot create a story with the same name " + storyName);
        storyNames.add(storyName);
        return new Story(storyName, initialEstimate);
    }

    public Story createStory(String storyName, int initialEstimate,String ... tags) throws Exception {
        if (storyNames.contains(storyName))
            throw new Exception("cannot create a story with the same name " + storyName);
        storyNames.add(storyName);
        return new Story(storyName, initialEstimate,tags);
    }

}
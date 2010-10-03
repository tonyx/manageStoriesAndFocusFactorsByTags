package org.tonyx;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 18-set-2010
 * Time: 20.28.21
 * To change this template use File | Settings | File Templates.
 */

public class Story {
    boolean done = false;
    private String _storyName;
    int _initialEstimate;
    private HashSet<String> _tags;
    private int _actual;

    public boolean isDone() {
        return done;
    }

    public int getActual() {
        return _actual;
    }

    @Override
    public String toString() {
        return "Story{" +
                "done=" + done +
                ", _storyName='" + _storyName + '\'' +
                ", _initialEstimate=" + _initialEstimate +
                ", _tags=" + _tags +
                '}';
    }

    private Story(String storyName, int initialEstimate) {
        _storyName=storyName;
        _initialEstimate = initialEstimate;
        _tags = new HashSet<String>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Story)) return false;

        Story story = (Story) o;

        if (_initialEstimate != story._initialEstimate) return false;
        if (done != story.done) return false;
        if (_storyName != null ? !_storyName.equals(story._storyName) : story._storyName != null) return false;
        if (_tags != null ? !_tags.equals(story._tags) : story._tags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (done ? 1 : 0);
        result = 31 * result + (_storyName != null ? _storyName.hashCode() : 0);
        result = 31 * result + _initialEstimate;
        result = 31 * result + (_tags != null ? _tags.hashCode() : 0);
        return result;
    }

    public Story(String storyName, int storyPoints, String ... tags) {
        _tags = new HashSet<String>();
        _storyName = storyName;
        _initialEstimate = storyPoints;
        for (String tag: tags) {
            this._tags.add(tag);
        }
    }

    public int getInitialEstimate() {
        return _initialEstimate;
    }

    public String getStoryName() {
        return _storyName;
    }

    public HashSet<String> getTags() {
        return _tags;
    }


    public void setActual(int actual) {
        _actual = actual;
        done = true;
    }

    public double getRecordedFocusFactor() {
        return  (double)(_initialEstimate)/(double)(_actual);
    }

    public int getEstimationGap() throws Exception {
        if (!done)
            throw new Exception("can't get delta if don't know actual");
        return _actual-_initialEstimate;
    }

}

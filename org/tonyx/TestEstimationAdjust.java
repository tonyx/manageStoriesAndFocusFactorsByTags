package org.tonyx;
import org.junit.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 18-set-2010
 * Time: 20.19.05
 * To change this template use File | Settings | File Templates.
 */

public class TestEstimationAdjust {

    StoriesFactory storiesFactory;

    @Before public void start()
    {
        storiesFactory = new StoriesFactory();
    }


    @Test public void can_initialize_story_with_tag_andGetIt()
    {
        Story firstStory = new Story("banana",10,"sweet");
        assertTrue(firstStory.getTags().contains("sweet"));
    }

    @Test public void can_add_stories_without_tags_and_the_resulting_size_should_be_zero() throws Exception
    {
        Story firstStory = new StoriesFactory().createStory("banana", 10);
        assertEquals(0,firstStory.getTags().size());
    }

    @Test public void should_be_able_to_add_more_than_one_tag_and_get_them()
    {
        Story firstStory = new Story("banana",10,"sweet","fruit");
        assertEquals(2,firstStory.getTags().size());
    }

    @Test public void the_same_tag_should_be_added_just_once()
    {
        Story firstStory = new Story("banana",10,"sweet","sweet","fruit");
        assertEquals(2,firstStory.getTags().size());
    }

    @Test public void should_be_able_to_add_actuals() throws Exception
    {
        Story firstStory = new Story("banana",10,"sweet","sweet","fruit");
        firstStory.setActual(15);
        assertEquals(5,firstStory.getEstimationGap());
    }


    /**
     * the constructor must be private and object must be built by the factory
     * @throws Exception
     */
    @Deprecated
    @Test(expected = Exception.class) public void cant_get_gap_if_actual_is_not_tracked() throws Exception
    {
        Story firstStory = new Story("banana",10,"sweet","sweet","fruit");
        firstStory.getEstimationGap();
    }

    @Test public void forcus_factor_sdfasfsd() throws Exception
    {
        Story firstStory = new StoriesFactory().createStory("banana", 10);
        firstStory.setActual(20);
        assertEquals(10,firstStory.getEstimationGap());
        assertEquals(0.5,firstStory.getRecordedFocusFactor(),0.1);
        assertEquals(.5,firstStory.getRecordedFocusFactor(),.1);
    }

    @Test public void forcus_factor_sdklfaslfsafa() throws Exception
    {
        Story firstStory = new StoriesFactory().createStory("banana", 5);
        firstStory.setActual(20);
        assertEquals(.25,firstStory.getRecordedFocusFactor(),.1);
    }

    @Test public void product_backlog_should_contains_all_the_Stories_that_I_put_there() throws Exception
    {
        Story firstStory = storiesFactory.createStory("banana",10,"sweet","fruit");
        Story secondStory = storiesFactory.createStory("apple",10,"fruit");
        Story thirdStory = storiesFactory.createStory("lemon",10,"fruit","acid");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(firstStory);
        productBacklog.add(secondStory);
        productBacklog.add(thirdStory);
        assertEquals(3,productBacklog.getStories().size());
    }

    @Test(expected = Exception.class) public void there_must_not_exist_two_stories_with_the_same_name() throws Exception
    {
        Story firstStory = storiesFactory.createStory("banana", 10);
        Story secondStory = storiesFactory.createStory("banana", 10);
    }

    @Test public void add_three_stories_to_sprint_backlog() throws Exception
    {

        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(storiesFactory.createStory("banana",10,"sweet","fruit"));
        productBacklog.add(storiesFactory.createStory("apple",10,"fruit"));
        productBacklog.add(storiesFactory.createStory("lemon",10,"fruit","acid"));
        productBacklog.add(storiesFactory.createStory("kiwi",10,"fruit","sweet"));

        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple","lemon");
        assertEquals(3,firstSprint.getStories().size());
    }

    @Test public void cant_add_unexisting_stories_to_sprint() throws Exception
    {
        ProductBacklog productBacklog = new ProductBacklog("my product");

        productBacklog.add(storiesFactory.createStory("banana",10,"sweet","fruit"));
        productBacklog.add(storiesFactory.createStory("apple",10,"fruit"));
        productBacklog.add(storiesFactory.createStory("lemon",10,"fruit","acid"));
        productBacklog.add(storiesFactory.createStory("kiwi",10,"fruit","sweet"));

        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","buu","apple","lemon");
        assertEquals(2,firstSprint.getStories().size());
    }

    @Test public void evaluate_focus_factor_of_sprint() throws Exception
    {

        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(storiesFactory.createStory("banana",10,"sweet","fruit"));
        productBacklog.add(storiesFactory.createStory("apple",10,"fruit"));
        productBacklog.add(storiesFactory.createStory("lemon",10,"fruit","acid"));
        productBacklog.add(storiesFactory.createStory("kiwi",10,"fruit","sweet"));

        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple","lemon");
        firstSprint.setDone("banana",20);
        firstSprint.setDone("apple",20);
        firstSprint.setDone("lemon",20);
        assertEquals(3,firstSprint.getStories().size());
        assertEquals(0.5,firstSprint.actualFocusFactor(),0.1);
    }

    @Test public void evaluate_focus_factor_of_sprint_2() throws Exception
    {
        ProductBacklog productBacklog = new ProductBacklog("my product");

        productBacklog.add(storiesFactory.createStory("banana",10,"sweet","fruit"));
        productBacklog.add(storiesFactory.createStory("apple",10,"fruit"));
        productBacklog.add(storiesFactory.createStory("lemon",10,"fruit","acid"));
        productBacklog.add(storiesFactory.createStory("kiwi",10,"fruit","sweet"));

        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple","lemon");
        firstSprint.setDone("banana",10);
        firstSprint.setDone("apple",10);
        firstSprint.setDone("lemon",10);
        assertEquals(3,firstSprint.getStories().size());
        assertEquals(1,firstSprint.actualFocusFactor(),0.1);
    }

    @Test public void evaluate_focus_factor_of_sprint_3() throws Exception
    {
        
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(storiesFactory.createStory("banana",10,"sweet","fruit"));
        productBacklog.add(storiesFactory.createStory("apple",10,"fruit"));
        productBacklog.add(storiesFactory.createStory("lemon",10,"fruit","acid"));
        productBacklog.add(storiesFactory.createStory("kiwi",10,"fruit","sweet"));

        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple","lemon");
        firstSprint.setDone("banana",30);
        firstSprint.setDone("apple",30);
        firstSprint.setDone("lemon",30);
        assertEquals(3,firstSprint.getStories().size());
        assertEquals(0.33,firstSprint.actualFocusFactor(),0.1);
    }

    @Test public void evaluate_focus_factor_of_sprint_4() throws Exception
    {
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(storiesFactory.createStory("banana",1,"sweet","fruit"));
        productBacklog.add(storiesFactory.createStory("apple",10,"fruit"));
        productBacklog.add(storiesFactory.createStory("lemon",9,"fruit","acid"));
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple","lemon");
        firstSprint.setDone("banana",2);
        firstSprint.setDone("apple",18);
        firstSprint.setDone("lemon",10);
        assertEquals(3,firstSprint.getStories().size());
        assertEquals(20.0/30.0,firstSprint.actualFocusFactor(),0.1);
    }


    @Test public void should_be_able_to_compute_correlation_betwenn_tags_and_estimation_gap() throws Exception {
        Story banana = storiesFactory.createStory("banana",20,"sweet","fruit");
        Story apple = storiesFactory.createStory("apple",10,"fruit");
        ProductBacklog productBacklog = new ProductBacklog("my product");

        productBacklog.add(banana);
        productBacklog.add(apple);
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple");
        firstSprint.setDone("banana",20);
        firstSprint.setDone("apple",10);
        assertEquals(2,firstSprint.getStories().size());
        assertTrue(banana.isDone());
        assertEquals(20,banana .getActual());
        assertTrue(apple.isDone());
        assertEquals(10,apple.getActual());
        assertTrue(apple.isDone());
        
        double actuals = banana .getActual()+ apple.getActual();
        double estimated = banana.getInitialEstimate()+apple.getInitialEstimate();
        assertEquals(estimated/actuals,firstSprint.actualFocusFactor(),0.1);
    }


    @Test public void sprint_backlog_consistent() throws Exception {
        Story banana = storiesFactory.createStory("banana",10,"sweet","fruit");
        Story apple = storiesFactory.createStory("apple",10,"fruit");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(banana);
        productBacklog.add(apple);
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple");
        assertEquals(banana,firstSprint.getStories().get("banana"));
        assertEquals(apple,firstSprint.getStories().get("apple"));

        Assert.assertFalse(banana.isDone());
        firstSprint.setDone("banana",30);
        assertTrue(banana.isDone());

        Assert.assertFalse(apple.isDone());
        firstSprint.setDone("apple",40);
        assertTrue(apple.isDone());

        assertEquals(30,banana.getActual());
        assertEquals(40,apple.getActual());
    }



    @Test public void should_be_able_to_get_actual_by_tag() throws Exception {
        Story banana = storiesFactory.createStory("banana",10,"sweet");
        Story apple = storiesFactory.createStory("apple",10,"plain");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(banana);
        productBacklog.add(apple);
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple");
        firstSprint.setDone("banana",20);
        Assert.assertFalse(apple.isDone());
        assertEquals(20,firstSprint.actualsByTag("sweet"),0.1);
    }

    @Test public void different_focus_factor_for_different_tags() throws Exception {
        Story banana = storiesFactory.createStory("banana",10,"sweet");
        Story apple = storiesFactory.createStory("apple",10,"plain");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(banana);
        productBacklog.add(apple);
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple");
        firstSprint.setDone("banana",20);
        firstSprint.setDone("apple",10);
        assertEquals(20,firstSprint.actualsByTag("sweet"),0.1);
        assertEquals(1,firstSprint.actualFocusFactoryByTag("plain"),0.1);
        assertEquals(0.5,firstSprint.actualFocusFactoryByTag("sweet"),0.1);
    }

    @Ignore
    @Test public void sprint_backlog_consistent_differentiate_focus_factor_multi_tag_perfect_match() throws Exception {
        Story banana = storiesFactory.createStory("banana",10,"fruit","sweet");
        Story apple = storiesFactory.createStory("apple",10,"fruit");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(banana);
        productBacklog.add(apple);
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","apple");
        assertEquals(banana,firstSprint.getStories().get("banana"));
        assertEquals(apple,firstSprint.getStories().get("apple"));
        firstSprint.setDone("banana",20);
        firstSprint.setDone("apple",10);
        assertEquals(20,firstSprint.actualsByPerfectMatchTags("fruit","sweet"),0.1);
        assertEquals(0.5,firstSprint.actualFocusFactoryByPerfectMatchTag("fruit","sweet"),0.1);
    }

    @Test public void sprint_backlog_consistent_differentiate_focus_factor_multi_tag_perfect_match_2() throws Exception {
        Story banana = storiesFactory.createStory("banana",10,"fruit","sweet");
        Story fragola = storiesFactory.createStory("fragola",10,"fruit","sweet");
        Story apple = storiesFactory.createStory("apple",10,"fruit");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(banana);
        productBacklog.add(fragola);
        productBacklog.add(apple);
        SprintBacklog firstSprint= productBacklog.produceSprint("firstSprint","banana","fragola");
        assertEquals(banana,firstSprint.getStories().get("banana"));
        assertEquals(fragola,firstSprint.getStories().get("fragola"));
        Assert.assertFalse(banana.isDone());
        firstSprint.setDone("banana",15);
        assertTrue(banana.isDone());
        Assert.assertFalse(fragola.isDone());
        firstSprint.setDone("fragola",5);
        assertTrue(fragola.isDone());
        assertEquals(20,firstSprint.actualsByPerfectMatchTags("fruit","sweet"),0.1);
        assertEquals(1,firstSprint.actualFocusFactoryByPerfectMatchTag("fruit","sweet"),0.1);
    }

    @Test
    public void getActualsByTagForSingleTag() throws Exception
    {
        Story fragola = storiesFactory.createStory("fragola",10,"fruit");
        Story coke = storiesFactory.createStory("coke",10,"liquid");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(fragola);
        productBacklog.add(coke);
        SprintBacklog firstSprint = productBacklog.produceSprint("firstSprint","fragola","coke");
        firstSprint.setDone("fragola",20);
        firstSprint.setDone("coke",10);
        assertEquals(0.5,firstSprint.actualFocusFactoryByTag("fruit"),0.1);
        assertEquals(1,firstSprint.actualFocusFactoryByTag("liquid"),0.1);
    }


    @Test
    public void getActualsByTagForMultiTaggedStories() throws Exception
    {
        Story fragola = storiesFactory.createStory("fragola",10,"fruit");
        Story coke = storiesFactory.createStory("coke",10,"liquid","sweet");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(fragola);
        productBacklog.add(coke);
        SprintBacklog firstSprint = productBacklog.produceSprint("firstSprint","fragola","coke");
        firstSprint.setDone("fragola",20);
        firstSprint.setDone("coke",10);
        assertEquals(0.5,firstSprint.actualFocusFactoryByTag("fruit"),0.1);
        assertEquals(1,firstSprint.actualFocusFactoryByTag("sweet"),0.1);
    }


    @Test
    public void getActualsByTagForMixedTag() throws Exception
    {
        Story fragola = storiesFactory.createStory("fragola",10,"fruit","sweet");
        Story coke = storiesFactory.createStory("coke",10,"liquid","sweet");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(fragola);
        productBacklog.add(coke);
        SprintBacklog firstSprint = productBacklog.produceSprint("firstSprint","fragola","coke");
        firstSprint.setDone("fragola",20);
        firstSprint.setDone("coke",10);
        assertEquals(0.5,firstSprint.actualFocusFactoryByTag("fruit"),0.1);
        assertEquals((10.0+10.0)/(10.0+20.0),firstSprint.actualFocusFactoryByTag("sweet"),0.1);
    }


    @Test
    public void getFocusFactorForMultiTag() throws Exception
    {
        Story fragola = storiesFactory.createStory("fragola",10,"fruit","sweet");
        Story coke = storiesFactory.createStory("coke",10,"liquid","sweet");
        ProductBacklog productBacklog = new ProductBacklog("my product");
        productBacklog.add(fragola);
        productBacklog.add(coke);
        SprintBacklog firstSprint = productBacklog.produceSprint("firstSprint","fragola","coke");
        firstSprint.setDone("fragola",20);
        firstSprint.setDone("coke",10);
        assertEquals(1,firstSprint.actualFocusFactoryByTag("liquid","sweet"),0.1);
        assertEquals(0.5,firstSprint.actualFocusFactoryByTag("fruit","sweet"),0.1);
    }


}





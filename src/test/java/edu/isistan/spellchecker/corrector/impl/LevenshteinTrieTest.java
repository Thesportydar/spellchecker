package edu.isistan.spellchecker.corrector.impl;

import edu.isistan.spellchecker.corrector.Trie;
import edu.isistan.spellchecker.tokenizer.TokenScanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LevenshteinTrieTest {
    private LevenshteinTrie corr;


    private Set<String> makeSet(String[] strings) {
        Set<String> mySet = new TreeSet<>();
        for (String s : strings) {
            mySet.add(s);
        }
        return mySet;
    }


    @Before
    public void setUp() throws IOException {
        Trie trie = new Trie(new TokenScanner(new FileReader("smallDictionary.txt")));
        corr = new LevenshteinTrie(trie);
    }


    @After
    public void tearDown() {
        corr = null;
    }


    @Test
    public void testConstructorInvalid() throws IOException {
        try{
            new LevenshteinTrie(null);
            fail("Expected an IllegalArgumentException - null dictionary.");
        }
        catch (IllegalArgumentException ex){
            //Do nothing - its supposed to throw an exception!
        }
    }


    @Test public void testDeletion() throws IOException {
        assertEquals("teh -> {eh,th,te}", makeSet(new String[]{"eh","th","te"}), corr.getDeletions("teh"));
    }


    @Test public void testInsert() throws IOException {
        assertEquals("ay -> {bay, cay, day, any, aye}",
                makeSet(new String[]{"bay", "cay", "day", "any", "aye"}),
                corr.getInsertions("ay"));
    }


    @Test public void testSubstitution() throws IOException {
        assertEquals("teh -> {heh, meh, tah, tea, tee, ten, tex}",
                makeSet(new String[]{"heh", "meh", "tah", "tea", "tee", "ten", "tex"}),
                corr.getSubstitutions("teh"));
    }


    @Test public void testCorrections() throws IOException {
        assertEquals("h -> {a, i, ah, eh, th}",
                makeSet(new String[]{"a", "i", "ah", "eh", "th"}),
                corr.getCorrections("h"));
    }


    @Test public void testCorrectionsCase() throws IOException {
        assertEquals("H -> {A, I, Ah, Eh, Th}",
                makeSet(new String[]{"A", "I", "Ah", "Eh", "Th"}),
                corr.getCorrections("H"));
    }


    @Test public void testNull() throws IOException {
        try {
            assertEquals(" null -> illegal argument", new TreeSet<String>(),
                    corr.getCorrections(null));
            fail("Should have thrown an illegal argument exception");
        } catch (IllegalArgumentException e) {
            // do nothing
        }
    }
}
package net.uaprom.jmorphy2.solr;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import net.uaprom.jmorphy2.test._BaseTestCase;


public class BaseFilterTestCase extends _BaseTestCase {
    protected static final Version LUCENE_VERSION = Version.LUCENE_47;

    protected void assertAnalyzesTo(Analyzer analyzer,
                                    String sent,
                                    List<String> expectedTokens,
                                    List<Integer> expectedPositions) throws IOException {
        TokenStream ts = analyzer.tokenStream("dummy", sent);
        CharTermAttribute termAtt = ts.getAttribute(CharTermAttribute.class);
        PositionIncrementAttribute posIncAtt = ts.getAttribute(PositionIncrementAttribute.class);
        List<String> tokens = new ArrayList<String>();
        List<Integer> positions = new ArrayList<Integer>();
        ts.reset();
        for (int i = 0; ts.incrementToken(); i++) {
            tokens.add(new String(termAtt.buffer(), 0, termAtt.length()));
            positions.add(new Integer(posIncAtt.getPositionIncrement()));
        }
        ts.close();
        assertEquals(expectedTokens, tokens);
        assertEquals(expectedPositions, positions);
    }
}
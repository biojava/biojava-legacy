/*
 */
package org.biojavax.bio.db.ncbi;

import junit.framework.TestCase;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.db.IllegalIDException;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author George waldon
 */
public class GenbankRichSequenceDBTest extends TestCase {
     String id = "NM_001110";  // an ID
    
    public void testeFetch() {
        GenbankRichSequenceDB ncbi = new GenbankRichSequenceDB();
        try {
            RichSequence rs = ncbi.getRichSequence(id);
        } catch (IllegalIDException e) {
            fail("IllegalIDException: "+ e);
        } catch (BioException e) {
            fail("Unexpected Bioexception: "+ e);
        }
    }
}

/*
 */
package org.biojavax.bio.db.ncbi;

import junit.framework.TestCase;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.db.IllegalIDException;
import org.biojavax.bio.seq.RichSequence;

/**
 *
 * @author George Waldon
 */
public class GenpeptRichSequenceDBTest extends TestCase {
     String id = "NP_001101";  // an ID
    
    public void testeFetch() {
        GenpeptRichSequenceDB ncbi = new GenpeptRichSequenceDB();
        try {
            RichSequence rs = ncbi.getRichSequence(id);
        } catch (IllegalIDException e) {
            fail("IllegalIEexception: "+ e);
        } catch (BioException e) {
            fail("Unexpected BioException: "+ e);
        }
    }
}

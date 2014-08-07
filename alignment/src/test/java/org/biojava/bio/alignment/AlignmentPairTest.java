/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 */
package org.biojava.bio.alignment;

import junit.framework.TestCase;

import org.biojava.bio.alignment.SubstitutionMatrix;

import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.GappedSequence;
import org.biojava.bio.seq.Sequence;

/**
 * Unit test for AlignmentPair.
 */
public final class AlignmentPairTest extends TestCase {

    public void testGetQuery() throws Exception {
        GappedSequence subject = DNATools.createGappedDNASequence("aaaattttaaaattttaaaa", "subject");
        GappedSequence query = DNATools.createGappedDNASequence("aaaaccccaaaa----aaaa", "query");
        SubstitutionMatrix substitutionMatrix = SubstitutionMatrix.getNuc4_4();
        AlignmentPair alignmentPair = new AlignmentPair(query, subject, substitutionMatrix);
        assertEquals(query, alignmentPair.getQuery());
    }

    public void testGetUngappedQuery() throws Exception {
        GappedSequence subject = DNATools.createGappedDNASequence("aaaattttaaaa----aaaa", "subject");
        Sequence query = DNATools.createDNASequence("aaaaccccaaaattttaaaa", "query");
        SubstitutionMatrix substitutionMatrix = SubstitutionMatrix.getNuc4_4();
        AlignmentPair alignmentPair = new AlignmentPair(query, subject, substitutionMatrix);
        GappedSequence gappedQuery = alignmentPair.getQuery();
        assertEquals(query.getName(), gappedQuery.getName());
        assertEquals(query.seqString(), gappedQuery.seqString());
    }

    public void testGetSubject() throws Exception {
        GappedSequence subject = DNATools.createGappedDNASequence("aaaattttaaaattttaaaa", "subject");
        GappedSequence query = DNATools.createGappedDNASequence("aaaaccccaaaa----aaaa", "query");
        SubstitutionMatrix substitutionMatrix = SubstitutionMatrix.getNuc4_4();
        AlignmentPair alignmentPair = new AlignmentPair(query, subject, substitutionMatrix);
        assertEquals(subject, alignmentPair.getSubject());
    }

    public void testGetUngappedSubject() throws Exception {
        Sequence subject = DNATools.createDNASequence("aaaattttaaaattttaaaa", "subject");
        GappedSequence query = DNATools.createGappedDNASequence("aaaaccccaaaa----aaaa", "query");
        SubstitutionMatrix substitutionMatrix = SubstitutionMatrix.getNuc4_4();
        AlignmentPair alignmentPair = new AlignmentPair(query, subject, substitutionMatrix);
        GappedSequence gappedSubject = alignmentPair.getSubject();
        assertEquals(subject.getName(), gappedSubject.getName());
        assertEquals(subject.seqString(), gappedSubject.seqString());
    }
}

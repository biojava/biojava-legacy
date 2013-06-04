/*
 * BioJava development code
 * 
 * This code may be freely distributed and modified under the terms of the GNU Lesser General Public Licence. This
 * should be distributed with the code. If you do not have a copy, see:
 * 
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * Copyright for this code is held jointly by the individual authors. These should be listed in @author doc comments.
 * 
 * For more information on the BioJava project and its aims, or to join the biojava-l mailing list, visit the home page
 * at:
 * 
 * http://www.biojava.org/
 * 
 * Created on 16.04.2013 Author: hannes
 */

package org.biojava.bio.program.scf;

import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.biojava.bio.chromatogram.UnsupportedChromatogramFormatException;
import org.junit.Assert;

public class SCFTest extends TestCase {

    public void testNoComment() throws UnsupportedChromatogramFormatException, IOException {
        final URL resource = getClass().getResource("nocomment.scf");
        SCF.create(resource.openStream(), 0L);
    }

    public void testNormal() throws UnsupportedChromatogramFormatException, IOException {
        final URL resource = getClass().getResource("normal.scf");
        SCF.create(resource.openStream(), 0L);
    }

    public void testOffsetWrong() throws UnsupportedChromatogramFormatException, IOException {
        final URL resource = getClass().getResource("offset.scf");
        SCF scf = SCF.create(resource.openStream(), 0L);
        String expected = "NNNNNNNNGNNNCNNNAGGNNGTCGTCTGCTGCTCGATGTCCTACACATGGACAGGCGCCTTGATNACACCATGCGCCGCGGA"
                + "GGAGAGCAAGCTGCCCATCAATGCGCTGAGCAACTCTTTGCTGCGTAACCATAACATGGTCTATGCCACAACATCCCGCAGCGCAAGCCAACG"
                + "GCAGAAGAAGGTTACCTTTGACAGACTGCAAGTCCTGGACGATCACTACCGGGACGTGCTTAAGGAGGTGAAGGCGAAGGCGTCCACAGTTAA"
                + "GGCTAAACTTCTATCTGTAGAAGAAGCCTGTAAACTGACGCCCCCACATTCGGCCAGATCTAAATTTGGCTATGGGGCAAAGGACGTCCGGAA"
                + "CCTATCCAGCAAGGCCGTTAACCACATCCGCTCCGTGTGGAAGGACTTGCTGGAAGACACTGAGACACCAATTGACACTACCATCATGGCAAA"
                + "AAATGAGGTTTTCTGCGTCCAACCAGAGAAAGGAGGCCGCAAGCCAGCACGCCTTATCGTATTCCCAGATCTGGGAGTTCGTGTGTGCGAGAA"
                + "AATGGCCCTTTATGACGTGGTCTCCACCCTTCCTCAGGCCGTGATGGGCTCCTCATACGGATTCCAGTACTCTCCTGGACAGCGGGTCGAGTT"
                + "CCTGGTGAATGCCTGGAAATCAAAGANAAANCCCCATGGGGTTCTCATATGACACCCGCTGTTTTGACTCAACGGTCACCGAGAGTGATATCC"
                + "GTGTTGAGGAGTCAATTTACCNATGTTGTGACTTGGCCCCCGAAGCCNGACAGGCNATAANN";
        Assert.assertEquals(expected, scf.getBaseCalls().symbolListForLabel(SCF.DNA).seqString().toUpperCase());
    }

}

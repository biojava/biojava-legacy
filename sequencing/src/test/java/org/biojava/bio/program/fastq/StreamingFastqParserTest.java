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
package org.biojava.bio.program.fastq;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

/**
 * Unit test for StreamingFastqParser.
 */
public class StreamingFastqParserTest extends TestCase
{

    public void testStreamNullReadable() throws Exception
    {
        try
        {
            StreamingFastqParser.stream((Readable) null, FastqVariant.FASTQ_SANGER, new StreamListener() {
                @Override
                public void fastq(final Fastq fastq) {
                    // empty
                }
            });
            fail("stream(null,,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testStreamNullVariant() throws Exception
    {
        try
        {
            final String input = "";
            StreamingFastqParser.stream(new StringReader(input), null, new StreamListener() {
                @Override
                public void fastq(final Fastq fastq) {
                    // empty
                }
            });
            fail("stream(,null,) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    public void testStreamNullListener() throws Exception
    {
        try
        {
            final String input = "";
            StreamingFastqParser.stream(new StringReader(input), FastqVariant.FASTQ_SANGER, null);
            fail("stream(,,null) expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}

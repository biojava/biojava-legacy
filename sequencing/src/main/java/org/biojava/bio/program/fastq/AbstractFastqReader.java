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

import java.net.URL;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.nio.charset.Charset;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import com.google.common.io.InputSupplier;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * Abstract reader implementation for FASTQ formatted sequences.
 *
 * @since 1.7.1
 */
abstract class AbstractFastqReader
    implements FastqReader
{

    /**
     * Return the FASTQ sequence format variant for this reader.
     *
     * @return the FASTQ sequence format variant for this reader
     */
    protected abstract FastqVariant getVariant();

    /**
     * Parse the specified readable.
     *
     * @since 1.9.1
     * @param readable readable, must not be null
     * @param listener low-level event based parser callback, must not be null
     * @throws IOException if an I/O error occurs
     */
    public final void parse(final Readable readable, final ParseListener listener)
        throws IOException
    {
        FastqParser.parse(readable, listener);
    }

    /**
     * Parse the specified input supplier.
     *
     * @since 1.8.2
     * @deprecated will be removed in version 1.10, see {@link #parse(Readable,ParseListener)}
     * @param supplier input supplier, must not be null
     * @param listener low-level event based parser callback, must not be null
     * @throws IOException if an I/O error occurs
     */
    public final <R extends Readable & Closeable> void parse(final InputSupplier<R> supplier,
                                                             final ParseListener listener)
        throws IOException
    {
        FastqParser.parse(supplier, listener);
    }

    /**
     * Stream the specified readable.
     *
     * @since 1.9.1
     * @param readable readable, must not be null
     * @param listener event based reader callback, must not be null
     * @throws IOException if an I/O error occurs
     */
    public final void stream(final Readable readable, final StreamListener listener)
        throws IOException
    {
        StreamingFastqParser.stream(readable, getVariant(), listener);
    }

    /**
     * Stream the specified input supplier.
     *
     * @since 1.8.2
     * @deprecated will be removed in version 1.10, see {@link #stream(Readable,StreamListener)}
     * @param supplier input supplier, must not be null
     * @param listener event based reader callback, must not be null
     * @throws IOException if an I/O error occurs
     */
    public final <R extends Readable & Closeable> void stream(final InputSupplier<R> supplier,
                                                              final StreamListener listener)
        throws IOException
    {
        StreamingFastqParser.stream(supplier, getVariant(), listener);
    }

    /** {@inheritDoc} */
    public final Iterable<Fastq> read(final File file) throws IOException
    {
        if (file == null)
        {
            throw new IllegalArgumentException("file must not be null");
        }

        BufferedReader reader = null;
        Collect collect = new Collect();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            stream(reader, collect);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return collect.getResult();
    }

    /** {@inheritDoc} */
    public final Iterable<Fastq> read(final URL url) throws IOException
    {
        if (url == null)
        {
            throw new IllegalArgumentException("url must not be null");
        }

        BufferedReader reader = null;
        Collect collect = new Collect();
        try
        {
            reader = Resources.asCharSource(url, Charset.forName("UTF-8")).openBufferedStream();
            stream(reader, collect);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return collect.getResult();
    }

    /** {@inheritDoc} */
    public final Iterable<Fastq> read(final InputStream inputStream) throws IOException
    {
        if (inputStream == null)
        {
            throw new IllegalArgumentException("inputStream must not be null");
        }

        BufferedReader reader = null;
        Collect collect = new Collect();
        try
        {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            stream(reader, collect);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
        return collect.getResult();
    }

    /**
     * Collect FASTQ formatted sequences in a list.
     */
    private static final class Collect implements StreamListener
    {
        /** List of FASTQ formatted sequences. */
        private final List<Fastq> result = Lists.newLinkedList();

        /** {@inheritDoc} */
        public void fastq(final Fastq fastq)
        {
            result.add(fastq);
        }

        /**
         * Return an unmodifiable iterable over the FASTQ formatted sequences collected by this stream listener.
         *
         * @return an unmodifiable iterable over the FASTQ formatted sequences collected by this stream listener
         */
        public Iterable<Fastq> getResult()
        {
            return Iterables.unmodifiableIterable(result);
        }
    }
}

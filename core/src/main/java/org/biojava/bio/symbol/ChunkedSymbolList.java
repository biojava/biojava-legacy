package org.biojava.bio.symbol;

import java.io.IOException;
import java.io.Serializable;
import org.biojava.bio.seq.io.ChunkedSymbolListFactory;
import org.biojava.utils.ChangeEvent;

import org.biojava.utils.ChangeListener;
import org.biojava.utils.ChangeSupport;
import org.biojava.utils.ChangeVetoException;

/**
 * SymbolList implementation using constant-size chunks. Each chunk provides
 * the same number of symbols (except the last one, which may be shorter). When
 * a request for symbols comes in, firstly the apropreate chunk is located, and
 * then the symbols are extracted. This implementation is used in the IO package
 * to avoid allocating and re-allocating memory when the total length of the
 * symbol list is unknown. It can also be used when chunks are to be lazily
 * fetched from some high-latency stoorage by implementing a single lazy-fetch
 * SymbolList class and populating a ChunkedSymbolList with a complete
 * tile-path of them.
 *
 * @author David Huen
 * @author Matthew Pocock
 * @author George Waldon
 */
public class ChunkedSymbolList
        extends AbstractSymbolList
        implements Serializable
{
  // state
  private SymbolList [] chunks;
  private final int chunkSize;
  private final Alphabet alpha;
  private int length;

  // cached info for speedups
  private transient int currentMin = Integer.MAX_VALUE;
  private transient int currentMax = Integer.MIN_VALUE;
  private transient SymbolList currentChunk = null;

  private void readObject(java.io.ObjectInputStream stream)
          throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();

    currentMin = Integer.MAX_VALUE;
    currentMax = Integer.MIN_VALUE;
    currentChunk = null;
  }

  protected void finalize() throws Throwable {
    super.finalize();
    alpha.removeChangeListener(ChangeListener.ALWAYS_VETO, Alphabet.SYMBOLS);
  }

  public ChunkedSymbolList(SymbolList [] chunks,
                           int chunkSize,
                           int length,
                           Alphabet alpha)
  {
    this.chunks = chunks;
    this.chunkSize = chunkSize;
    this.length = length;
    this.alpha = alpha;
    alpha.addChangeListener(ChangeListener.ALWAYS_VETO, Alphabet.SYMBOLS);
  }

  public Alphabet getAlphabet() {
    return alpha;
  }

  public int length() {
    return length;
  }

  public synchronized Symbol symbolAt(int pos) {
    int offset;
    --pos;

    if ((pos < currentMin) || (pos > currentMax)) {
      // bad - we are outside the current chunk
      int chnk = pos / chunkSize;
      offset =  pos % chunkSize;

      currentMin = pos - offset;
      currentMax = currentMin + chunkSize - 1;
      currentChunk = chunks[chnk];
    }
    else {
      offset = pos - currentMin;
    }

    return currentChunk.symbolAt(offset + 1);
  }

  public SymbolList subList(int start, int end) {
    if (start < 1 || end > length()) {
      throw new IndexOutOfBoundsException(
              "Sublist index out of bounds " + length() + ":" + start + "," + end
      );
    }

    if (end < start) {
      throw new IllegalArgumentException(
              "end must not be lower than start: start=" + start + ", end=" + end
      );
    }

    //
    // Mildly optimized for case where from and to are within
    // the same chunk.
    //

    int afrom = start - 1;
    int ato = end - 1;
    int cfrom = afrom / chunkSize;
    if (ato / chunkSize == cfrom) {
      return chunks[cfrom].subList((afrom % chunkSize) + 1, (ato % chunkSize) + 1);
    } else {
      return super.subList(start, end);
    }
  }
  
    public void edit(Edit edit) throws IllegalAlphabetException,
            ChangeVetoException {
        ChangeSupport cs;
        ChangeEvent cevt;
        Symbol[] dest;

        // first make sure that it is in bounds
        if ((edit.pos + edit.length > length +1 ) || (edit.pos <= 0) || edit.length < 0){
            throw new IndexOutOfBoundsException();
        }
        // make sure that the symbolList is of the correct alphabet
        if (( edit.replacement.getAlphabet() != getAlphabet()) &&  (edit.replacement != SymbolList.EMPTY_LIST)){
            throw new IllegalAlphabetException();
        }

        // give the listeners a change to veto this
         // create a new change event ->the EDIT is a static final variable of type ChangeType in SymbolList interface
        cevt = new ChangeEvent(this, SymbolList.EDIT, edit);
        cs = getChangeSupport(SymbolList.EDIT);
        synchronized(cs) {
            // let the listeners know what we want to do
            cs.firePreChangeEvent(cevt);
        
            // now for the edit
            int posRightFragInSourceArray5 = edit.pos + edit.length - 1;
            int rightFragLength = length - posRightFragInSourceArray5;
            int posRightFragInDestArray5 = posRightFragInSourceArray5 + edit.replacement.length() - edit.length;
            int posReplaceFragInDestArray5 = edit.pos - 1;
            int replaceFragLength = edit.replacement.length();
            int newLength = length + replaceFragLength - edit.length;

            // extend the array
            dest = new Symbol[newLength];
            
            // copy symbols before the edit and make sure we are not editing the edit at the same time (hoops!)
            int leftFragLength = edit.pos - 1;
            for (int i = 0; i < chunks.length; i++) {
                SymbolList chunk = chunks[i];
                Symbol[] chunkSymbol = ((SimpleSymbolList) chunk).getSymbolArray();
                int desStart = i * chunkSize;
                int desEnd = i * chunkSize + chunk.length();
                if (desEnd >= leftFragLength) {
                    desEnd = leftFragLength;
                    System.arraycopy(chunkSymbol, 0, dest, desStart, desEnd - desStart);
                    break;
                }
                System.arraycopy(chunkSymbol, 0, dest, desStart, desEnd - desStart);
            }
            
            // copy the symbols after the edit
            if (rightFragLength > 0) {
                int chunkNum = posRightFragInSourceArray5 / chunkSize;
                int chunkStart = posRightFragInSourceArray5 % chunkSize;
                int chunkCopied = 0;
                for (int i = chunkNum; i < chunks.length; i++) {
                    SymbolList chunk = chunks[i];
                    Symbol[] chunkSymbol = ((SimpleSymbolList) chunk).getSymbolArray();
                    int desStart = posRightFragInDestArray5 + chunkCopied;
                    int desLength = chunk.length() - chunkStart;
                    int left = rightFragLength - chunkCopied;
                    if (left == 0) {
                        break;
                    }
                    if (desLength > left) {
                        desLength = left;
                    }
                    System.arraycopy(chunkSymbol, chunkStart, dest, desStart, desLength);
                    if (chunkStart != 0) {
                        chunkStart = 0;
                    }
                    chunkCopied += desLength;
                }
            }

            // copy the symbols within the edit
            for (int i = 1; i <= replaceFragLength; i++) {
                dest[posReplaceFragInDestArray5 + i - 1] = edit.replacement.symbolAt(i);
            }

            //Make the chunks
            int chunknum = newLength/chunkSize;
            int chunkleft = newLength%chunkSize;
            int chunkReused = (edit.pos-1)/chunkSize;
            if(chunkleft>0) chunknum++;
            SymbolList [] symListArray = new SymbolList[chunknum];
            for(int i=0;i<chunknum-1;i++) {
                if(i<chunkReused) {
                    symListArray[i] = chunks[i];
                    continue;
                }
                Symbol[] chunk = new Symbol[chunkSize];
                System.arraycopy(dest, i*chunkSize, chunk, 0, chunkSize);
                symListArray[i] = new SimpleSymbolList(chunk, chunkSize, getAlphabet());
            }
            if(chunkleft>0) {
                Symbol[] chunk = new Symbol[chunkleft];
                System.arraycopy(dest, (chunknum-1)*chunkSize, chunk, 0, chunkleft);
                symListArray[chunknum-1] = new SimpleSymbolList(chunk, chunkleft, getAlphabet());
            } else {
                Symbol[] chunk = new Symbol[chunkSize];
                System.arraycopy(dest, (chunknum-1)*chunkSize, chunk, 0, chunkSize);
                symListArray[chunknum-1] = new SimpleSymbolList(chunk, chunkSize, getAlphabet());
            }

            chunks = symListArray;
            length = newLength;
            currentMin = Integer.MAX_VALUE;
            currentMax = Integer.MIN_VALUE;
            currentChunk = null;

            cs.firePostChangeEvent(cevt);
        }
    }
}

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

package org.biojava.bio.symbol;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.biojava.utils.SingletonList;

/**
 * <p>
 * Encapsulates an edit operation on a SymbolList.
 * See {@link org.biojava.bio.symbol.SymbolList
 * SymbolList} for a full description.
 * </p>
 *
 * @author Matthew Pocock
 * @author George Waldon
 */
public final class Edit implements Serializable {
  public final int pos;
  public final int length;
  public final SymbolList replacement;
  public final Map<String, Object> props;

  /**
   * Create a new Edit.
   *
   * @param pos the start of the edit
   * @param length the length of the edit
   * @param replacement a SymbolList representing the symbols that replace those from pos to
   *        pos + length-1 inclusive
   */
  public Edit(int pos, int length, SymbolList replacement) {
    this.pos = pos;
    this.length = length;
    this.replacement = replacement;
    props = Collections.emptyMap();
  }
  
  /**
   * Create a new Edit with some properties.
   *
   * @param pos the start of the edit
   * @param length the length of the edit
   * @param replacement a SymbolList representing the symbols that replace those from pos to
   *        pos + length-1 inclusive
   * @param props a map of String properties and associated objects adding 
   *        metainformation to this edit; for example a property may describe
   *        the fate of features surrounding this edit.
   */
  public Edit(int pos, int length, SymbolList replacement, Map<String, Object> props) {
    this.pos = pos;
    this.length = length;
    this.replacement = replacement;
    this.props = Collections.unmodifiableMap(new HashMap<String, Object>(props));
  }

  /**
   * Convenience construtor for making single residue changes
   *
   * @param pos the position of the change
   * @param alpha the <code>Alphabet</code> of the replacement <code>Symbol</code>
   * @param replacement the replacement <code>Symbol</code>
   * @throws IllegalSymbolException if the replacement <code>Symbol</code> is not contained in <code>alpha</code>
   */
  public Edit(int pos, Alphabet alpha, Symbol replacement) throws IllegalSymbolException{
    this.pos = pos;
    this.length = 1;
    SymbolList sl = new SimpleSymbolList(
        alpha, new SingletonList(replacement));
    this.replacement = sl;
    props = Collections.emptyMap();
  }
}


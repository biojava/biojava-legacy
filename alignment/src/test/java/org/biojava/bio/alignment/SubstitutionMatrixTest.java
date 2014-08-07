/*
 *                  BioJava development code
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
 * Created on Aug 23, 2007
 *
 */

package org.biojava.bio.alignment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;

import junit.framework.TestCase;

import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.io.SymbolTokenization;
import org.biojava.bio.symbol.AlphabetManager;
import org.biojava.bio.symbol.FiniteAlphabet;
import org.biojava.bio.symbol.Symbol;

public class SubstitutionMatrixTest extends TestCase {



	public void testParseSubstitutionMatrix(){

		InputStream inStream = this.getClass().getResourceAsStream("/blosum62.mat");
        assertNotNull(inStream);


        try {
            FiniteAlphabet alphabet = (FiniteAlphabet) AlphabetManager.alphabetForName("PROTEIN-TERM");
            SymbolTokenization symtok = alphabet.getTokenization("token");
        	//String file = readMatrix(inStream);
        	//SubstitutionMatrix matrix = new SubstitutionMatrix(alphabet,file,"blosum 62");
        	SubstitutionMatrix matrix = SubstitutionMatrix.getSubstitutionMatrix(
        			new BufferedReader(new InputStreamReader(inStream)));
        	//matrix.printMatrix();

        	Symbol A = symtok.parseToken("A");
        	Symbol W = symtok.parseToken("W");
        	Symbol D = symtok.parseToken("D");


        	assertEquals(matrix.getValueAt(A, A), 4);
        	assertEquals(matrix.getValueAt(W, D),-4);
        } catch (Exception e){
        	fail(e.getMessage());
        }


	}


	private String readMatrix(InputStream stream) throws IOException{
		String newline = System.getProperty("line.separator");
		BufferedReader reader = new BufferedReader(new InputStreamReader( stream));
		StringBuffer file = new StringBuffer();
		while (reader.ready()){
			file.append(reader.readLine() );
			file.append(newline);
		}

		return file.toString();
	}

    public void testGetBlosum100() {
        assertNotNull(SubstitutionMatrix.getBlosum100());
    }

    public void testGetBlosum100_50() {
        assertNotNull(SubstitutionMatrix.getBlosum100_50());
    }

    public void testGetBlosum30() {
        assertNotNull(SubstitutionMatrix.getBlosum30());
    }

    public void testGetBlosum30_50() {
        assertNotNull(SubstitutionMatrix.getBlosum30_50());
    }

    public void testGetBlosum35() {
        assertNotNull(SubstitutionMatrix.getBlosum35());
    }

    public void testGetBlosum35_50() {
        assertNotNull(SubstitutionMatrix.getBlosum35_50());
    }

    public void testGetBlosum40() {
        assertNotNull(SubstitutionMatrix.getBlosum40());
    }

    public void testGetBlosum40_50() {
        assertNotNull(SubstitutionMatrix.getBlosum40_50());
    }

    public void testGetBlosum45() {
        assertNotNull(SubstitutionMatrix.getBlosum45());
    }

    public void testGetBlosum45_50() {
        assertNotNull(SubstitutionMatrix.getBlosum45_50());
    }

    public void testGetBlosum50() {
        assertNotNull(SubstitutionMatrix.getBlosum50());
    }

    public void testGetBlosum50_50() {
        assertNotNull(SubstitutionMatrix.getBlosum50_50());
    }

    public void testGetBlosum55() {
        assertNotNull(SubstitutionMatrix.getBlosum55());
    }

    public void testGetBlosum55_50() {
        assertNotNull(SubstitutionMatrix.getBlosum55_50());
    }

    public void testGetBlosum60() {
        assertNotNull(SubstitutionMatrix.getBlosum60());
    }

    public void testGetBlosum60_50() {
        assertNotNull(SubstitutionMatrix.getBlosum60_50());
    }

    public void testGetBlosum62() {
        assertNotNull(SubstitutionMatrix.getBlosum62());
    }

    public void testGetBlosum62_50() {
        assertNotNull(SubstitutionMatrix.getBlosum62_50());
    }

    public void testGetBlosum65() {
        assertNotNull(SubstitutionMatrix.getBlosum65());
    }

    public void testGetBlosum65_50() {
        assertNotNull(SubstitutionMatrix.getBlosum65_50());
    }

    public void testGetBlosum70() {
        assertNotNull(SubstitutionMatrix.getBlosum70());
    }

    public void testGetBlosum70_50() {
        assertNotNull(SubstitutionMatrix.getBlosum70_50());
    }

    public void testGetBlosum75() {
        assertNotNull(SubstitutionMatrix.getBlosum75());
    }

    public void testGetBlosum75_50() {
        assertNotNull(SubstitutionMatrix.getBlosum75_50());
    }

    public void testGetBlosum80() {
        assertNotNull(SubstitutionMatrix.getBlosum80());
    }

    public void testGetBlosum80_50() {
        assertNotNull(SubstitutionMatrix.getBlosum80_50());
    }

    public void testGetBlosum85() {
        assertNotNull(SubstitutionMatrix.getBlosum85());
    }

    public void testGetBlosum85_50() {
        assertNotNull(SubstitutionMatrix.getBlosum85_50());
    }

    public void testGetBlosum90() {
        assertNotNull(SubstitutionMatrix.getBlosum90());
    }

    public void testGetBlosum90_50() {
        assertNotNull(SubstitutionMatrix.getBlosum90_50());
    }

    public void testGetBlosumn() {
        assertNotNull(SubstitutionMatrix.getBlosumn());
    }

    public void testGetBlosumn_50() {
        assertNotNull(SubstitutionMatrix.getBlosumn_50());
    }

    public void testGetDayhoff() {
        assertNotNull(SubstitutionMatrix.getDayhoff());
    }

    public void testGetGonnet() {
        assertNotNull(SubstitutionMatrix.getGonnet());
    }

    public void testGetIdentity() {
        assertNotNull(SubstitutionMatrix.getIdentity());
    }

    public void testGetMatch() {
        assertNotNull(SubstitutionMatrix.getMatch());
    }

    public void testGetNuc4_2() {
        assertNotNull(SubstitutionMatrix.getNuc4_2());
    }

    public void testGetNuc4_4() {
        assertNotNull(SubstitutionMatrix.getNuc4_4());
    }

    public void testGetPam10() {
        assertNotNull(SubstitutionMatrix.getPam10());
    }

    public void testGetPam100() {
        assertNotNull(SubstitutionMatrix.getPam100());
    }

    public void testGetPam110() {
        assertNotNull(SubstitutionMatrix.getPam110());
    }

    public void testGetPam120() {
        assertNotNull(SubstitutionMatrix.getPam120());
    }

    public void testGetPam130() {
        assertNotNull(SubstitutionMatrix.getPam130());
    }

    public void testGetPam140() {
        assertNotNull(SubstitutionMatrix.getPam140());
    }

    public void testGetPam150() {
        assertNotNull(SubstitutionMatrix.getPam150());
    }

    public void testGetPam160() {
        assertNotNull(SubstitutionMatrix.getPam160());
    }

    public void testGetPam170() {
        assertNotNull(SubstitutionMatrix.getPam170());
    }

    public void testGetPam180() {
        assertNotNull(SubstitutionMatrix.getPam180());
    }

    public void testGetPam190() {
        assertNotNull(SubstitutionMatrix.getPam190());
    }

    public void testGetPam20() {
        assertNotNull(SubstitutionMatrix.getPam20());
    }

    public void testGetPam200() {
        assertNotNull(SubstitutionMatrix.getPam200());
    }

    public void testGetPam210() {
        assertNotNull(SubstitutionMatrix.getPam210());
    }

    public void testGetPam220() {
        assertNotNull(SubstitutionMatrix.getPam220());
    }

    public void testGetPam230() {
        assertNotNull(SubstitutionMatrix.getPam230());
    }

    public void testGetPam240() {
        assertNotNull(SubstitutionMatrix.getPam240());
    }

    public void testGetPam250() {
        assertNotNull(SubstitutionMatrix.getPam250());
    }

    public void testGetPam260() {
        assertNotNull(SubstitutionMatrix.getPam260());
    }

    public void testGetPam270() {
        assertNotNull(SubstitutionMatrix.getPam270());
    }

    public void testGetPam280() {
        assertNotNull(SubstitutionMatrix.getPam280());
    }

    public void testGetPam290() {
        assertNotNull(SubstitutionMatrix.getPam290());
    }

    public void testGetPam30() {
        assertNotNull(SubstitutionMatrix.getPam30());
    }

    public void testGetPam300() {
        assertNotNull(SubstitutionMatrix.getPam300());
    }

    public void testGetPam310() {
        assertNotNull(SubstitutionMatrix.getPam310());
    }

    public void testGetPam320() {
        assertNotNull(SubstitutionMatrix.getPam320());
    }

    public void testGetPam330() {
        assertNotNull(SubstitutionMatrix.getPam330());
    }

    public void testGetPam340() {
        assertNotNull(SubstitutionMatrix.getPam340());
    }

    public void testGetPam350() {
        assertNotNull(SubstitutionMatrix.getPam350());
    }

    public void testGetPam360() {
        assertNotNull(SubstitutionMatrix.getPam360());
    }

    public void testGetPam370() {
        assertNotNull(SubstitutionMatrix.getPam370());
    }

    public void testGetPam380() {
        assertNotNull(SubstitutionMatrix.getPam380());
    }

    public void testGetPam390() {
        assertNotNull(SubstitutionMatrix.getPam390());
    }

    public void testGetPam40() {
        assertNotNull(SubstitutionMatrix.getPam40());
    }

    public void testGetPam400() {
        assertNotNull(SubstitutionMatrix.getPam400());
    }

    public void testGetPam410() {
        assertNotNull(SubstitutionMatrix.getPam410());
    }

    public void testGetPam420() {
        assertNotNull(SubstitutionMatrix.getPam420());
    }

    public void testGetPam430() {
        assertNotNull(SubstitutionMatrix.getPam430());
    }

    public void testGetPam440() {
        assertNotNull(SubstitutionMatrix.getPam440());
    }

    public void testGetPam450() {
        assertNotNull(SubstitutionMatrix.getPam450());
    }

    public void testGetPam460() {
        assertNotNull(SubstitutionMatrix.getPam460());
    }

    public void testGetPam470() {
        assertNotNull(SubstitutionMatrix.getPam470());
    }

    public void testGetPam480() {
        assertNotNull(SubstitutionMatrix.getPam480());
    }

    public void testGetPam490() {
        assertNotNull(SubstitutionMatrix.getPam490());
    }

    public void testGetPam50() {
        assertNotNull(SubstitutionMatrix.getPam50());
    }

    public void testGetPam500() {
        assertNotNull(SubstitutionMatrix.getPam500());
    }

    public void testGetPam60() {
        assertNotNull(SubstitutionMatrix.getPam60());
    }

    public void testGetPam70() {
        assertNotNull(SubstitutionMatrix.getPam70());
    }

    public void testGetPam80() {
        assertNotNull(SubstitutionMatrix.getPam80());
    }

    public void testGetPam90() {
        assertNotNull(SubstitutionMatrix.getPam90());
    }

    public void testGetSubstitutionMatrixNullAlphabet() throws Exception {
        try {
            SubstitutionMatrix.getSubstitutionMatrix(null, new BufferedReader(new StringReader("")));
            fail("NPE expected");
        }
        catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetSubstitutionMatrixNullReader() throws Exception {
        try {
            SubstitutionMatrix.getSubstitutionMatrix(DNATools.getDNA(), null);
            fail("NPE expected");
        }
        catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetSubstitutionMatrixNameNullAlphabet() throws Exception {
        try {
            SubstitutionMatrix.getSubstitutionMatrix(null, new BufferedReader(new StringReader("")), "name");
            fail("NPE expected");
        }
        catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetSubstitutionMatrixNameNullReader() throws Exception {
        try {
            SubstitutionMatrix.getSubstitutionMatrix(DNATools.getDNA(), null, "name");
            fail("NPE expected");
        }
        catch (NullPointerException e) {
            // expected
        }
    }

    public void testGetSubstitutionMatrixNullName() throws Exception {
        try {
            SubstitutionMatrix.getSubstitutionMatrix(DNATools.getDNA(), new BufferedReader(new StringReader("")), null);
            fail("NPE expected");
        }
        catch (NullPointerException e) {
            // expected
        }
    }

    public void testSerializable() {
        assertTrue(SubstitutionMatrix.getNuc4_4() instanceof Serializable);
    }

    public void testSerialization() throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buffer);
        out.writeObject(SubstitutionMatrix.getNuc4_4());
        out.close();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        Object dest = in.readObject();
        in.close();

        assertTrue(dest instanceof SubstitutionMatrix);
    }
}

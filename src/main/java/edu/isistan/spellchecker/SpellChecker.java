package edu.isistan.spellchecker;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

import edu.isistan.spellchecker.corrector.AbstractDictionary;
import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.tokenizer.TokenScanner;

/**
 * El SpellChecker usa un Dictionary, un Corrector, and I/O para chequear
 * de forma interactiva un stream de texto. Despues escribe la salida corregida
 * en un stream de salida. Los streams pueden ser archivos, sockets, o cualquier
 * otro stream de Java.
 * <p>
 * Nota:
 * <ul>
 * <li> La implementaci�n provista provee m�todos utiles para implementar el SpellChecker.
 * <li> Toda la salida al usuario deben enviarse a System.out (salida estandar)
 * </ul>
 * <p>
 * El SpellChecker es usado por el SpellCkecherRunner. Ver:
 * @see SpellCheckerRunner
 */
public class SpellChecker {
    private final Corrector corr;
    private final AbstractDictionary dict;

    /**
     * Constructor del SpellChecker
     *
     * @param c un Corrector
     * @param d un AbstractDictionary
     */
    public SpellChecker(Corrector c, AbstractDictionary d) {
        corr = c;
        dict = d;
    }

    /**
     * Returna un entero desde el Scanner provisto. El entero estar� en el rango [min, max].
     * Si no se ingresa un entero o este est� fuera de rango, repreguntar�.
     *
     * @param min
     * @param max
     * @param sc
     */
    private int getNextInt(int min, int max, Scanner sc) {
        while (true) {
            try {
                int choice = Integer.parseInt(sc.next());
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException ex) {
                // Was not a number. Ignore and prompt again.
            }
            System.out.println("Entrada invalida. Pruebe de nuevo!");
        }
    }

    /**
     * Retorna el siguiente String del Scanner.
     *
     * @param sc
     */
    private String getNextString(Scanner sc) {
        return sc.next();
    }

    /**
     * checkDocument interactivamente chequea un archivo de texto..
     * Internamente, debe usar un TokenScanner para parsear el documento.
     * Tokens de tipo palabra que no se encuentran en el diccionario deben ser corregidos
     * ; otros tokens deben insertarse tal cual en en documento de salida.
     * <p>
     *
     * @param in stream donde se encuentra el documento de entrada.
     * @param input entrada interactiva del usuario. Por ejemplo, entrada estandar System.in
     * @param out stream donde se escribe el documento de salida.
     * @throws IOException si se produce alg�n error leyendo el documento.
     */
    public void checkDocument(Reader in, InputStream input, Writer out) throws IOException {
        Scanner sc = new Scanner(input);
        TokenScanner ts = new TokenScanner(in);

        while (ts.hasNext()) {
            String token = ts.next();
            if (TokenScanner.isWord(token) && !dict.isWord(token)) {
                Set<String> corrections = corr.getCorrections(token);
                token = chooseCorrection(token, corrections, sc);
            }
            out.write(token);
        }
    }

    /**
     * Dada una palabra que no pertenece al diccionario y un set de correciones
     * solicita al usuario que accion desea tomar. Ignorar, ingresar la correccion
     * manualmente, o seleccionar una del conjunto.
     * La salida al usuario se envia por la salida estandar.
     *
     * @param token Palabra mal escrita
     * @param corrections Conjunto de potenciales correciones
     * @param sc Entrada interactiva del usuario donde indicara la accion a tomar
     * @return Palabra corregida (puede ser el mismo token de entrada)
     */
    public String chooseCorrection(String token, Set<String> corrections, Scanner sc) {
        LinkedList<String> options = new LinkedList<>(corrections);

        // display options
        System.out.println("The word: \"" + token + "\" is not in the dictionary. Please enter the number corresponding with the appropriate action\n0: Ignore and continue\n1:Replace with another word");
        if (!corrections.isEmpty()) {
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + 2 + ": Replace with \"" + options.get(i) +"\"");
            }
        }

        // get option
        int opt = getNextInt(0, corrections.size()+1, sc);

        if (opt == 0) {
            return token;
        } else if (opt == 1) {
            return getNextString(sc);
        } else {
            return options.get(opt - 2);
        }
    }
}

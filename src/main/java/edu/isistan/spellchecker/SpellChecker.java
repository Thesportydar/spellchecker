package edu.isistan.spellchecker;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

import edu.isistan.spellchecker.corrector.Corrector;
import edu.isistan.spellchecker.corrector.Dictionary;
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
    private Corrector corr;
    private Dictionary dict;

    /**
     * Constructor del SpellChecker
     *
     * @param c un Corrector
     * @param d un Dictionary
     */
    public SpellChecker(Corrector c, Dictionary d) {
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
                String correction = chooseCorrection(corrections, sc);
                System.out.println();
                if (correction != null) {
                    token = correction;
                }
            }
            out.write(token);
        }
    }

    public String chooseCorrection(Set<String> corrections, Scanner sc) {
        LinkedList<String> options = new LinkedList<>(corrections);

        // display options
        System.out.println(0 + ") " + "Ignorar");
        System.out.println(1 + ") " + "Ingresar manualmente..");
        if (!corrections.isEmpty()) {
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + 2 + ") " + options.get(i));
            }
        }

        // get option
        int opt = getNextInt(0, corrections.size()+1, sc);

        if (opt == 0) {
            return null;
        } else if (opt == 1) {
            System.out.println("Ingresa la correccion: ");
            return getNextString(sc);
        } else {
            return options.get(opt - 2);
        }
    }
}

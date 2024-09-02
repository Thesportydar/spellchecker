package edu.isistan.spellchecker.corrector;

import edu.isistan.spellchecker.tokenizer.TokenScanner;

public abstract class AbstractDictionary {
    /**
     * Consume todas los tokens del token scanner y agrega aquellos que
     * son palabras y no se encuentran ya en el diccionario
     * @param ts TokenScanner
     */
    protected void loadWords(TokenScanner ts) {
        String token;

        while (ts.hasNext()) {
            token = ts.next();

            if (TokenScanner.isWord(token) && !isWord(token)) {
                add(token);
            }
        }
    }

    /**
     * Agrega una palabra al diccionario
     * Recordar que es case insensitive
     * @param word
     * @return true si se pudo agregar la palabra al diccionario
     */
    public abstract boolean add(String word);

    /**
     * Retorna el n�mero de palabras correctas en el diccionario.
     * Recuerde que como es case insensitive si Dogs y doGs est�n en el
     * diccionario, cuentan como una sola palabra.
     *
     * @return n�mero de palabras �nicas
     */
    public abstract int getNumWords();

    /**
     * Testea si una palabra es parte del diccionario. Si la palabra no est� en
     * el diccionario debe retornar false. null debe retornar falso.
     * Si en el diccionario est� la palabra Dog y se pregunta por la palabra dog
     * debe retornar true, ya que es case insensitive.
     *Llamar a este m�todo no debe reabrir el archivo de palabras.
     *
     * @param word verifica si la palabra est� en el diccionario.
     * Asuma que todos los espacios en blanco antes y despues de la palabra fueron removidos.
     * @return si la palabra est� en el diccionario.
     */
    public abstract boolean isWord(String word);
}

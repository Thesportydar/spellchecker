package edu.isistan.spellchecker.tokenizer;

import java.util.Iterator;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;

/**
 * Dado un archivo provee un m�todo para recorrerlo.
 */
public class TokenScanner implements Iterator<String> {
	
	private static final char APOSTROFE = '\'';
	private java.io.Reader fileReader;
	private int currentChar;
  /**
   * Crea un TokenScanner.
   * <p>
   * Como un iterador, el TokenScanner solo debe leer lo justo y
   * necesario para implementar los m�todos next() y hasNext(). 
   * No se debe leer toda la entrada de una.
   * <p>
   *
   * @param in fuente de entrada
   * @throws IOException si hay alg�n error leyendo.
   * @throws IllegalArgumentException si el Reader provisto es null
   */
  public TokenScanner(java.io.Reader in) throws IOException {
	  if (in == null)
		  throw new IllegalArgumentException("El reader provisto es null");
	  this.fileReader = in;
	  try {
		  this.currentChar = fileReader.read();
	  } catch (IOException e) {
		  throw new RuntimeException("Error leyendo el archivo", e);
	  }
  }

  /**
   * Determina si un car�cer es una caracter v�lido para una palabra.
   * <p>
   * Un caracter v�lido es una letra (
   * Character.isLetter) o una apostrofe '\''.
   *
   * @param c 
   * @return true si es un caracter
   */
  public static boolean isWordCharacter(int c) {
	if ( Character.isLetter(c) || c == APOSTROFE)
			 return true;
	return false;
  }


   /**
   * Determina si un string es una palabra v�lida.
   * Null no es una palabra v�lida.
   * Un string que todos sus caracteres son v�lidos es una 
   * palabra. Por lo tanto, el string vac�o es una palabra v�lida.
   * @param s 
   * @return true si el string es una palabra.
   */
  public static boolean isWord(String s) {
	  if (s == null || s.length() == 0)
		  return false;
	  for (char c : s.toCharArray()) {
		  if (!isWordCharacter(c))
			  return false;
	  }
	  return true;
  }

  /**
   * Determina si hay otro token en el reader.
   */
  public boolean hasNext() {
	  return currentChar > 0;
  }

  /**
   * Retorna el siguiente token.
   *
   * @throws NoSuchElementException cuando se alcanz� el final de stream
   */
  public String next() {
	  if (!hasNext())
		  throw new NoSuchElementException("No hay más tokens en el reader");
	  
	  StringBuilder buffer = new StringBuilder();
	  
	  try {
		  while (hasNext() && isWordCharacter(currentChar)) {
			  buffer.append((char) currentChar);
			  this.currentChar = this.fileReader.read();
		  }
		  if (buffer.length() == 0)
			  while (hasNext() && !isWordCharacter(currentChar)) {
				  buffer.append((char) currentChar);
				  this.currentChar = this.fileReader.read();
			  }
	  } catch (IOException e) {
		  throw new RuntimeException("Error leyendo el archivo", e);
	  }
	
	  return buffer.toString();
  }
}

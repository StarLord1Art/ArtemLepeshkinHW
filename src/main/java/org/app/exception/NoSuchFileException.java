package org.app.exception;

import lombok.experimental.StandardException;

@StandardException
public class NoSuchFileException extends Exception{
  public NoSuchFileException(String message) {super(message);}
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author lukstankovic
 */
public class WrongSizeOfMapException extends RuntimeException {

	@Override
	public String getMessage() {
		return "Wrong size of map. Size of map must be at least 4 and size must be odd";
	}
}

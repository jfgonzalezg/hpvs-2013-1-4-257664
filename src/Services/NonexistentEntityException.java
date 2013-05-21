/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

/**
 *
 * @author Home
 */
public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}

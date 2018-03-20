

/**
 * An interface for algorithms that proceed iteratively.
 *
 */
public interface IterativeContext 
{
	/**
	 * Advances one step.
	 */
	void step();

	/**
	 * Returns true if this iterative process is finished, and false otherwise.
	 */
	boolean done();
}

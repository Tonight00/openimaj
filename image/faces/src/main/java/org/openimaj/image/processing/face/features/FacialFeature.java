package org.openimaj.image.processing.face.features;

import org.openimaj.image.processing.face.parts.DetectedFace;

/**
 * A FacialFeature is a feature describing a face that has
 * been detected. FacialFeatures can be compared to each other,
 * and to newly detected faces.
 * 
 * @author Jonathon Hare <jsh2@ecs.soton.ac.uk>
 *
 * @param <T>
 */
public abstract class FacialFeature<T extends FacialFeature<T>> {
	/**
	 * Default constructor. Internally calls {@link #initialise(DetectedFace)).
	 * @param face the face to build this FacialFeature from
	 */
	public FacialFeature(DetectedFace face) {
		
	}
		
	/**
	 * Initialise the state of this FacialFeature based on the 
	 * provided face.
	 * @param face the face
	 */
	protected abstract void initialise(DetectedFace face);
		
	/**
	 * Compare this feature against a the given feature and return
	 * a score.
	 * @param feature the feature
	 * @return the score for the match. 
	 */
	public abstract double compare(T feature);
}

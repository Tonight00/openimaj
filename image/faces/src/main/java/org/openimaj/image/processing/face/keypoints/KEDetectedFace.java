/**
 * Copyright (c) 2011, The University of Southampton and the individual contributors.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * 	Redistributions of source code must retain the above copyright notice,
 * 	this list of conditions and the following disclaimer.
 *
 *   *	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 *
 *   *	Neither the name of the University of Southampton nor the names of its
 * 	contributors may be used to endorse or promote products derived from this
 * 	software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openimaj.image.processing.face.keypoints;

import org.openimaj.image.FImage;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.keypoints.FacialKeypoint.FacialKeypointType;
import org.openimaj.math.geometry.shape.Rectangle;

/**
 * A K(eypoint)E(nriched)DetectedFace models a face detected 
 * by a face detector, together with the locations of 
 * certain facial features localised on the face.
 * 
 * @author Jonathon Hare <jsh2@ecs.soton.ac.uk>
 *
 */
public class KEDetectedFace extends DetectedFace {
	/**
	 * A list of detected facial keypoints. The coordinates are given in
	 * terms of the facePatch image. To project them into the original
	 * image you need to translate by bounds.x and bounds.y.
	 */
	protected FacialKeypoint [] keypoints;
	
	public KEDetectedFace(Rectangle bounds, FImage patch, FacialKeypoint[] keypoints) {
		super(bounds, patch);
		
		this.keypoints = keypoints;
	}
	
	public FacialKeypoint getKeypoint(FacialKeypointType type) {
		if (keypoints[type.ordinal()].type == type)
			return keypoints[type.ordinal()];
		
		for (FacialKeypoint part : keypoints) 
			if (part.type == type) 
				return part;
		
		return null;
	}

	public FacialKeypoint[] getKeypoints() {
		return keypoints;
	}
}

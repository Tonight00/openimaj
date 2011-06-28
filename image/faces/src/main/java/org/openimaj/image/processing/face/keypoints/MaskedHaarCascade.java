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
import org.openimaj.image.processing.algorithm.SummedAreaTable;


class MaskedHaarCascade {
	static FImage maskedHaarCascade(SummedAreaTable integralImage, int wh, int ww, int [][] H, double [][] TA, boolean [][] M) {
		//const double	*II = mxGetPr(prhs[0]);
		int ih = integralImage.data.height - 1;
		int iw = integralImage.data.width - 1;

		//const int	*H = (const int *) mxGetData(prhs[2]);
		int nf = H.length;
		int nh = H[0].length;

		// retarget haar features from window size to image size
		int	[] HI = new int[nf * nh];
		for (int h=0, dp=0; h < nh; h++) {
			for (int f = 0; f < nf; f++, dp++) {
				if (H[f][h] != 0) {
					int sign, ind;
					if (H[f][h] < 0) {
						sign = -1;
						ind = -H[f][h];
					} else {
						sign = 1;
						ind = H[f][h];
					}

					int x = (ind - 1) / (wh + 1);
					int y = ind - 1 - x * (wh + 1);

					HI[dp] = (x * (ih + 1) + y + 1) * sign;
				}
			}
		}

		FImage Q = new FImage(iw,ih);
		Q.fill(Float.NEGATIVE_INFINITY);

		int	x1 = ww / 2, x2 = iw - 1 - ww / 2;
		int	y1 = wh / 2, y2 = ih - 1 - wh / 2;
		int	coloff = ih + y1 - y2 - 1;

		int II = -1;
		for (int x = x1; x <= x2; x++, II += coloff + 1) {
			for (int y = y1; y <= y2; y++, II++) {
				if (M[y][x]) {
					int hp = 0; //HI;
					int tap = 0;
					float q = 0;

					for (int h = 0; h < nh; h++, tap ++) {
						if (HI[hp] == 0) {
							// end of cascade level

							if (q < TA[1][tap]) {
								q = Float.NEGATIVE_INFINITY;		// failed
								break;
							}
							if (h + 1 == nh) {
								q -= TA[1][tap];	// final level passed
								break;
							}

							q = 0;				// intermediate level passed
							hp += nf;
						} else {
							float s = 0;

							for (int f = 0; f < nf; f++, hp++) {
								if (HI[hp] == 0) {
									hp += nf - f;
									break;
								}

								if (HI[hp] < 0) {
									int index = II - HI[hp];
									int ix = index/(ih+1);
									int iy = index-ix*(ih+1);

									s -= integralImage.data.pixels[iy][ix];
								} else {
									int index = II + HI[hp];
									int ix = index/(ih+1);
									int iy = index-ix*(ih+1);
									s += integralImage.data.pixels[iy][ix];
								}
							}

							if (s >= TA[0][tap]) {
								q += TA[1][tap];
							}
							else {
								q -= TA[1][tap];
							}
						}
					}
					Q.pixels[y][x] = q;
				}
			}
		}

		return Q;
	}
}

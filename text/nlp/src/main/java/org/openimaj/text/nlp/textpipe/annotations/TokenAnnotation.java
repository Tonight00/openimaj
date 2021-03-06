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
package org.openimaj.text.nlp.textpipe.annotations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openimaj.text.nlp.tokenisation.ReversableToken;

/**
 * A {@link TokenAnnotation} represents a single token generated by a tokeniser.
 * It can be further annotated. It is aware of its off-sets in the
 * RawStringAnnotation that generated it.
 * 
 * @author Laurence Willmore (lgw1e10@ecs.soton.ac.uk)
 * 
 */
public class TokenAnnotation extends TextPipeAnnotation implements ReversableToken {
	String stringToken;
	String raw;
	public int start;
	public int stop;

	public TokenAnnotation(String token,String raw, int start, int stop) {
		super();
		this.stringToken = token;
		this.start = start;
		this.stop = stop;
		this.raw=raw;
	}

	public String getStringToken() {
		return stringToken;
	}	

	@Override
	public String getRawString() {
		return raw;
	}

	@Override
	public String reverse(List<? extends ReversableToken> tokens) {
		List<String> raws = new ArrayList<String>();
		for(ReversableToken tok:tokens){
			raws.add(tok.getRawString());
		}
		return StringUtils.join(raws,"");
	}

}

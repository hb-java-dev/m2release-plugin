/*
 * The MIT License
 * 
 * Copyright (c) 2009, NDS Group Ltd., James Nord, CloudBees, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jvnet.hudson.plugins.m2release;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.maven.shared.release.versions.DefaultVersionInfo;
import org.apache.maven.shared.release.versions.VersionInfo;
import org.apache.maven.shared.release.versions.VersionParseException;

@SuppressWarnings({"rawtypes","unchecked"})
public class M2ReleaseVersionInfo extends DefaultVersionInfo {

	private static final String INDEX_PREFIX = "Index-"; //$NON-NLS-1$
	
	private int nextVersionModeIndex = -1;

	public M2ReleaseVersionInfo(final String version, final String nextDevelopmentVersionMode) throws VersionParseException {
		super(version);
		// custom field (set next version mode index, acceptable are 1,2 and 3)
		// if latest is coming, we keep there DefaultVersionInfo behavior
		if (StringUtils.startsWith(nextDevelopmentVersionMode, INDEX_PREFIX)) {
			final String indexStr = StringUtils.substring(nextDevelopmentVersionMode, INDEX_PREFIX.length());
			nextVersionModeIndex = NumberUtils.toInt(indexStr) - 1;
		}
	}
	
	public M2ReleaseVersionInfo(final List digits, final int nextVersionModeIndex) {
		super(digits, null, null, null, null, null, null);
		this.nextVersionModeIndex = nextVersionModeIndex;
	}


	@Override
	public VersionInfo getNextVersion() {
		List digits = getDigits();
		
		// when index isn't valid, keep here DefaultVersionInfo behavior
		if (nextVersionModeIndex == -1 || digits == null || digits.size() == 0) {
			return super.getNextVersion();
		}
		
		List newDigits = new ArrayList(getDigits());
		int index = nextVersionModeIndex;
		if (index >= newDigits.size()) {
			// check if index isn't out of range of version digits, if it's we have to correct it
			index = newDigits.size() - 1;
		}
		
		// increment version digit by specified index
		newDigits.set(index, incrementVersionString((String)digits.get(index)));
		
		return new M2ReleaseVersionInfo(newDigits, nextVersionModeIndex);
	}

}

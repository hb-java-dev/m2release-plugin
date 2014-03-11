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

import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSet;
import hudson.model.BallColor;
import hudson.model.Describable;
import hudson.model.Descriptor;

@SuppressWarnings("rawtypes")
public class MavenDependencyResult implements Describable {

	private MavenModuleSet project;
	
	public MavenDependencyResult(final MavenModuleSet project) {
		this.project = project;
	}

	public String getUrl() {
		return project.getAbsoluteUrl();
	}

	public String getDisplayName() {
		return project.getFullDisplayName();
	}
	
	public String getDescription() {
		return project.getDescription();
	}

	public BallColor getIcon() {
		return project.getIconColor();
	}
	
	public String getVersion() {
		for (final MavenModule module : project.getItems()) {
			// return first version
			return module.getVersion();
		}
		return "Version unknown!"; //$NON-NLS-1$
	}
	
	public boolean isSnapshotVersion() {
		return getVersion().endsWith("-SNAPSHOT"); //$NON-NLS-1$
	}
	
	@Override
	public Descriptor getDescriptor() {
		return new MavenDependencyResultDescriptor();
	}

}

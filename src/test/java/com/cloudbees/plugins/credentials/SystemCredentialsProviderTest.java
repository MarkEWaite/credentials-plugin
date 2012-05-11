/*
 * The MIT License
 *
 * Copyright (c) 2011-2012, CloudBees, Inc., Stephen Connolly.
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
package com.cloudbees.plugins.credentials;

import com.cloudbees.plugins.credentials.impl.DummyCredentials;
import org.apache.commons.io.FileUtils;
import org.jvnet.hudson.test.HudsonTestCase;

public class SystemCredentialsProviderTest extends HudsonTestCase {

    public void testSaveAndLoad() throws Exception {
        assertTrue(CredentialsProvider.lookupCredentials(Credentials.class).isEmpty());
        SystemCredentialsProvider.getInstance().save();
        assertTrue(new SystemCredentialsProvider().getCredentials().isEmpty());
        SystemCredentialsProvider.getInstance().getCredentials().add(
                new DummyCredentials(CredentialsScope.SYSTEM, "foo", "bar"));
        assertFalse(CredentialsProvider.lookupCredentials(Credentials.class).isEmpty());
        assertTrue(new SystemCredentialsProvider().getCredentials().isEmpty());
        SystemCredentialsProvider.getInstance().save();
        assertFalse(new SystemCredentialsProvider().getCredentials().isEmpty());
    }

    public void testMalformedInput() throws Exception {
        assertTrue(CredentialsProvider.lookupCredentials(Credentials.class).isEmpty());
        SystemCredentialsProvider.getInstance().getCredentials().add(
                new DummyCredentials(CredentialsScope.SYSTEM, "foo", "bar"));
        assertFalse(CredentialsProvider.lookupCredentials(Credentials.class).isEmpty());
        assertTrue(new SystemCredentialsProvider().getCredentials().isEmpty());
        SystemCredentialsProvider.getInstance().save();
        assertFalse(new SystemCredentialsProvider().getCredentials().isEmpty());
        FileUtils.writeStringToFile(SystemCredentialsProvider.getConfigFile().getFile(), "<<barf>>");
        assertTrue(new SystemCredentialsProvider().getCredentials().isEmpty());
    }

    public void testSmokes() throws Exception {
        assertEquals(true, !CredentialsProvider.allCredentialsDescriptors().isEmpty());
        assertNotNull(SystemCredentialsProvider.getInstance().getDisplayName());
        assertNotNull(SystemCredentialsProvider.getInstance().getIconFileName());
        assertNotNull(SystemCredentialsProvider.getInstance().getUrlName());
        assertNotNull(SystemCredentialsProvider.getInstance().getDescriptor());
        assertNotNull(SystemCredentialsProvider.getInstance().getCredentials());
        assertNotNull(SystemCredentialsProvider.getInstance().getTarget());
    }

}
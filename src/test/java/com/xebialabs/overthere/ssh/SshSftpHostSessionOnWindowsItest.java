/*
 * Copyright (c) 2008-2010 XebiaLabs B.V. All rights reserved.
 *
 * Your use of XebiaLabs Software and Documentation is subject to the Personal
 * License Agreement.
 *
 * http://www.xebialabs.com/deployit-personal-edition-license-agreement
 *
 * You are granted a personal license (i) to use the Software for your own
 * personal purposes which may be used in a production environment and/or (ii)
 * to use the Documentation to develop your own plugins to the Software.
 * "Documentation" means the how to's and instructions (instruction videos)
 * provided with the Software and/or available on the XebiaLabs website or other
 * websites as well as the provided API documentation, tutorial and access to
 * the source code of the XebiaLabs plugins. You agree not to (i) lease, rent
 * or sublicense the Software or Documentation to any third party, or otherwise
 * use it except as permitted in this agreement; (ii) reverse engineer,
 * decompile, disassemble, or otherwise attempt to determine source code or
 * protocols from the Software, and/or to (iii) copy the Software or
 * Documentation (which includes the source code of the XebiaLabs plugins). You
 * shall not create or attempt to create any derivative works from the Software
 * except and only to the extent permitted by law. You will preserve XebiaLabs'
 * copyright and legal notices on the Software and Documentation. XebiaLabs
 * retains all rights not expressly granted to You in the Personal License
 * Agreement.
 */

package com.xebialabs.overthere.ssh;

import com.xebialabs.deployit.ci.Host;
import com.xebialabs.deployit.ci.HostAccessMethod;
import com.xebialabs.deployit.ci.OperatingSystemFamily;
import com.xebialabs.overthere.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SshSftpHostSessionOnWindowsItest {

	private Host windowsBox;

	@Before
	public void setupWindowsItestEnvironment() {
		windowsBox = new Host();
		windowsBox.setAddress("win-xp");
		windowsBox.setUsername("Administrator");
		windowsBox.setPassword("deployit");
		windowsBox.setTemporaryDirectoryLocation("c:\\temp");
		windowsBox.setOperatingSystemFamily(OperatingSystemFamily.WINDOWS);
		windowsBox.setAccessMethod(HostAccessMethod.SSH_SFTP);
	}

	@Test
	@Ignore("Needs Windows image that is not on dexter")
	public void writeTemporaryFileAndTypeIt() {
		final String expectedOutput = "Mary had a little lamb";

		HostSession hs = HostSessionFactory.getHostSession(windowsBox);
		try {
			HostFile tempFile = hs.getTempFile("testoutput", ".txt");

			HostFileUtils.putStringToHostFile(expectedOutput + "\r\nwhose fleece was white as snow\r\n", tempFile);

			CapturingCommandExecutionCallbackHandler capturedOutput = new CapturingCommandExecutionCallbackHandler();
			final String p = tempFile.getPath();
			System.out.println("-->" + capturedOutput);
			hs.execute(capturedOutput, "type", p);

			assertTrue(capturedOutput.getOutput().contains(expectedOutput));
		} finally {
			hs.close();
		}
	}

	@Test
	@Ignore("Needs Windows image that is not on dexter")
	public void mkdirs() {

		HostSession hs = HostSessionFactory.getHostSession(windowsBox);
		try {
			HostFile tempFile3 = hs.getFile("c:\\temp\\level1\\level2\\level3");
			tempFile3.mkdirs();

			final HostFile file = tempFile3.getFile("foo.txt");

			HostFileUtils.putStringToHostFile("hello....\r\n", file);

		} finally {
			hs.close();
		}
	}

}
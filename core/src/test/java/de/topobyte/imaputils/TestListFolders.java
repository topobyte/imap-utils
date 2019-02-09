// Copyright 2019 Sebastian Kuerten
//
// This file is part of imap-utils.
//
// imap-utils is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// imap-utils is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with imap-utils. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.imaputils;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Store;

import de.topobyte.imaputils.config.Config;
import de.topobyte.imaputils.config.ConfigUtil;

public class TestListFolders
{

	public static void main(String[] args) throws IOException
	{
		Config config = ConfigUtil.read();
		if (config == null) {
			return;
		}

		Password.Result result = Password.get();
		if (result.isValid()) {
			config.setPassword(new String(result.getPassword()));
			try {
				run(config);
			} catch (IOException | MessagingException e) {
				System.out.println("Connection failed: " + e.getMessage());
				System.exit(1);
			}
		}

		System.exit(0);
	}

	private static void run(Config config)
			throws MessagingException, IOException
	{
		try (Store store = TestUtil.connect(config.getHost(),
				config.getUsername(), config.getPassword())) {
			FolderLister tool = new FolderLister(store);
			tool.execute();
		}
	}

}
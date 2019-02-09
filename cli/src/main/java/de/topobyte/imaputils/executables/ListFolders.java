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

package de.topobyte.imaputils.executables;


import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Store;

import org.apache.commons.cli.Options;

import de.topobyte.imaputils.ConnectionUtil;
import de.topobyte.imaputils.FolderLister;
import de.topobyte.imaputils.config.Config;
import de.topobyte.imaputils.config.ConfigUtil;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class ListFolders
{

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			return new CommonsCliExeOptions(options, "[options]");
		}

	};

	public static void main(String name, CommonsCliArguments arguments)
	{
		Config config = ConfigUtil.obtainConfig();
		if (config == null) {
			System.exit(1);
		}

		try {
			run(config);
		} catch (IOException | MessagingException e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		System.exit(0);
	}

	private static void run(Config config)
			throws MessagingException, IOException
	{
		try (Store store = ConnectionUtil.connect(config)) {
			FolderLister tool = new FolderLister(store);
			tool.execute();
		}
	}

}

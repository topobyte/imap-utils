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

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import com.sun.mail.imap.IMAPFolder;

import de.topobyte.imaputils.ConnectionUtil;
import de.topobyte.imaputils.config.Config;
import de.topobyte.imaputils.config.ConfigUtil;
import de.topobyte.imaputils.processors.ListIdFinder;
import de.topobyte.imaputils.processors.MaxDaysStopCondition;
import de.topobyte.imaputils.processors.NeverStopCondition;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class ListListIds
{

	private static final String OPTION_DAYS = "days";

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			OptionHelper.addL(options, OPTION_DAYS, true, false,
					"number of days",
					"Stop processing mails when encountering an email that is older than the specified number of days");
			return new CommonsCliExeOptions(options, "[options]");
		}

	};

	public static void main(String name, CommonsCliArguments arguments)
	{
		Config config = ConfigUtil.obtainConfig();
		if (config == null) {
			System.exit(1);
		}

		ListListIds move = new ListListIds();

		CommandLine line = arguments.getLine();
		if (line.hasOption(OPTION_DAYS)) {
			String sDays = line.getOptionValue(OPTION_DAYS);
			int days = Integer.parseInt(sDays);

			move.stopAfterMaxDays = true;
			move.maxDays = days;
		}

		try {
			move.run(config, "INBOX");
		} catch (MessagingException e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		System.exit(0);
	}

	private boolean stopAfterMaxDays = false;
	private int maxDays = 0;

	private void run(Config config, String folderName) throws MessagingException
	{
		try (Store store = ConnectionUtil.connect(config)) {
			try (IMAPFolder folder = (IMAPFolder) store.getFolder(folderName)) {
				if (!folder.isOpen()) {
					folder.open(Folder.READ_WRITE);
				}

				ListIdFinder listIdFinder = listIdFinder(folder);
				listIdFinder.execute();
			}
		}
	}

	private ListIdFinder listIdFinder(IMAPFolder folder)
	{
		if (!stopAfterMaxDays) {
			return new ListIdFinder(folder, new NeverStopCondition());
		} else {
			return new ListIdFinder(folder, new MaxDaysStopCondition(maxDays));
		}
	}

}

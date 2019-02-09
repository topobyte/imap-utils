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

package de.topobyte.imaputil;

import java.io.IOException;

import javax.mail.FetchProfile;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.sun.mail.imap.IMAPFolder;

public class FolderSizeTool
{

	private IMAPFolder folder;

	public FolderSizeTool(IMAPFolder folder)
	{
		this.folder = folder;
	}

	public void execute() throws IOException, MessagingException
	{
		FetchProfile fp = new FetchProfile();
		fp.add(FetchProfile.Item.ENVELOPE);
		Message[] messages = folder.getMessages();
		folder.fetch(messages, fp);

		int size = 0;
		for (int i = 0; i < messages.length; i++) {
			Message msg = messages[i];
			size += msg.getSize();
		}

		System.out.println(String.format("size: %d", size));
	}

}

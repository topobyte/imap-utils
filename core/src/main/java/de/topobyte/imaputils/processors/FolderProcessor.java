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

package de.topobyte.imaputils.processors;

import java.util.Arrays;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.mail.imap.IMAPFolder;

public abstract class FolderProcessor
{

	private IMAPFolder folder;

	public FolderProcessor(IMAPFolder folder)
	{
		this.folder = folder;
	}

	public abstract boolean done(Message msg);

	public abstract void process(Message msg) throws MessagingException;

	public void execute() throws MessagingException
	{
		List<Message> messages = Arrays.asList(folder.getMessages());

		int i = 0;
		for (Message msg : Lists.reverse(messages)) {

			if (done(msg)) {
				break;
			}

			System.out.println(Strings.repeat("*", 72));
			System.out.println(String.format("MESSAGE %d:", (i++ + 1)));

			long id = folder.getUID(msg);
			System.out.println(id);

			Address[] recipients = msg.getAllRecipients();

			System.out.println("Subject: " + msg.getSubject());
			System.out.println("From: " + msg.getFrom()[0]);
			System.out.println("Reply to: " + msg.getReplyTo()[0]);
			if (recipients != null && recipients.length != 0) {
				System.out.println("To: " + recipients[0]);
			}
			System.out.println("Date: " + msg.getReceivedDate());
			System.out.println("Size: " + msg.getSize());
			System.out.println(msg.getFlags());

			process(msg);
		}
	}

}

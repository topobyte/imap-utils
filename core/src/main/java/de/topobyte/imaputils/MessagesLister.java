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

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.google.common.base.Strings;
import com.sun.mail.imap.IMAPFolder;

public class MessagesLister
{

	private IMAPFolder folder;

	public MessagesLister(IMAPFolder folder)
	{
		this.folder = folder;
	}

	public void execute() throws MessagingException
	{
		examine(folder);
	}

	private static void examine(IMAPFolder folder) throws MessagingException
	{
		Message[] messages = folder.getMessages();

		System.out.println("# Messages : " + folder.getMessageCount());
		System.out.println(
				"# Unread Messages : " + folder.getUnreadMessageCount());
		System.out.println(messages.length);

		for (int i = 0; i < messages.length; i++) {
			System.out.println(Strings.repeat("*", 72));
			System.out.println(String.format("MESSAGE %d:", (i + 1)));
			Message msg = messages[i];

			long id = folder.getUID(msg);
			System.out.println(id);

			Address[] recipients = msg.getAllRecipients();

			System.out.println("Subject: " + msg.getSubject());
			System.out.println("From: " + msg.getFrom()[0]);
			if (recipients != null && recipients.length != 0) {
				System.out.println("To: " + recipients[0]);
			}
			System.out.println("Date: " + msg.getReceivedDate());
			System.out.println("Size: " + msg.getSize());
			System.out.println("Flags: " + msg.getFlags());
			System.out.println("Content type: " + msg.getContentType());
			try {
				System.out.println("Body: \n" + msg.getContent());
			} catch (MessagingException | IOException e) {
				System.out.println(
						"Unable to get body content(): " + e.getMessage());
			}
		}
	}

}

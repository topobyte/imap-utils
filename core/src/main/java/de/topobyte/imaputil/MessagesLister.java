package de.topobyte.imaputil;

import java.io.IOException;

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

	public void execute() throws MessagingException, IOException
	{
		examine(folder);
	}

	private static void examine(IMAPFolder folder)
			throws MessagingException, IOException
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

			System.out.println("Subject: " + msg.getSubject());
			System.out.println("From: " + msg.getFrom()[0]);
			System.out.println("To: " + msg.getAllRecipients()[0]);
			System.out.println("Date: " + msg.getReceivedDate());
			System.out.println("Size: " + msg.getSize());
			System.out.println(msg.getFlags());
			System.out.println("Body: \n" + msg.getContent());
			System.out.println(msg.getContentType());
		}
	}

}

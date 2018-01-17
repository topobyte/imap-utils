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

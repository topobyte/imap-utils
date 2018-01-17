package de.topobyte.imaputil;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

import com.google.common.base.Strings;

public class FolderLister
{

	private Store store;

	public FolderLister(Store store)
	{
		this.store = store;
	}

	public void execute() throws MessagingException
	{
		Folder mainFolder = store.getDefaultFolder();
		list(mainFolder, 0);
	}

	private void list(Folder folder, int depth) throws MessagingException
	{
		Folder[] folders = folder.list();
		for (Folder child : folders) {
			System.out.println(String.format("%s%s", Strings.repeat(" ", depth),
					child.getName()));
			list(child, depth + 1);
		}
	}

}

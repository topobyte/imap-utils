package de.topobyte.imaputils.processors;

import javax.mail.Message;

import com.sun.mail.imap.IMAPFolder;

public abstract class StopConditionFolderProcessor extends FolderProcessor
{

	private MessageStopCondition stopCondition;

	public StopConditionFolderProcessor(IMAPFolder folder,
			MessageStopCondition stopCondition)
	{
		super(folder);
		this.stopCondition = stopCondition;
	}

	@Override
	public boolean done(Message msg)
	{
		return stopCondition.done(msg);
	}

}
